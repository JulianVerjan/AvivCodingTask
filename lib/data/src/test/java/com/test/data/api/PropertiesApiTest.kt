package com.test.data.api

import com.test.network.BuildConfig
import com.test.network.NetworkResponse
import com.test.network.adapter.NetworkResponseAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.random.Random

const val FETCH_PROPERTIES_STATUS_200 = "mock-responses/get-properties-status200.json"
const val FETCH_PROPERTY_STATUS_200 = "mock-responses/get-property-status200.json"
const val STATUS_ERROR = "mock-responses/get-properties-status404.json"

@ExperimentalCoroutinesApi
class PropertiesApiTest {

    private lateinit var propertiesApi: PropertiesApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {

        mockWebServer = MockWebServer()
        propertiesApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.API_BASE_URL))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(PropertiesApi::class.java)
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a Properties request With Status 200 Then the content is the same as the mocked one`() = runTest {
        enqueueResponse(FETCH_PROPERTIES_STATUS_200)
        val response = propertiesApi.fetchProperties()
        assert(response is NetworkResponse.Success)
    }

    @Test
    fun `Given a Property request With Status 200 Then the content is the same as the mocked one`() = runTest {
        enqueueResponse(FETCH_PROPERTY_STATUS_200)
        val response = propertiesApi.fetchPropertyById(listingId = Random.nextInt())
        assert(response is NetworkResponse.Success)
    }

    @Test
    fun `Given a Properties request With Status 404 error Then the content is the same as the mocked one`() = runTest {
        enqueueResponse(STATUS_ERROR)
        val response = propertiesApi.fetchProperties()
        assert(response is NetworkResponse.ApiError)
        assertNull(response)
    }

    private fun enqueueResponse(filePath: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
        val bufferSource = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()

        mockWebServer.enqueue(
            mockResponse.setBody(
                bufferSource!!.readString(Charsets.UTF_8)
            )
        )
    }
}
