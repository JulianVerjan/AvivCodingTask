package com.test.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import kotlin.random.Random
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

const val FETCH_PROPERTIES_STATUS_200 = "mock-responses/get-properties-status200.json"
const val FETCH_PROPERTY_STATUS_200 = "mock-responses/get-property-status200.json"
const val STATUS_ERROR = "mock-responses/get-properties-status404.json"

@ExperimentalCoroutinesApi
class PropertiesApiTest {

    private lateinit var propertiesApi: PropertiesApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        val moshi: Moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        mockWebServer = MockWebServer()
        propertiesApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
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
    fun `Given a Properties request With Status 200 Then the content is the same as the mocked one`() =
        runTest {
            enqueueResponse(FETCH_PROPERTIES_STATUS_200)
            val response = propertiesApi.fetchProperties()
            assertTrue(response is NetworkResponse.Success)
        }

    @Test
    fun `Given a Property request With Status 200 Then the content is the same as the mocked one`() =
        runTest {
            enqueueResponse(FETCH_PROPERTY_STATUS_200)
            val response = propertiesApi.fetchPropertyById(listingId = Random.nextInt())
            assertTrue(response is NetworkResponse.Success)
        }

    @Test
    fun `Given a Properties request With Status 404 error Then response is NetworkResponse_UnknownError type`() =
        runTest {
            enqueueResponse(STATUS_ERROR)
            val response = propertiesApi.fetchProperties()
            assertTrue(response is NetworkResponse.UnknownError)
            assertNotNull(response)
        }

    @Test
    fun `Given a Property request With Status 404 error Then response is NetworkResponse_UnknownError type`() =
        runTest {
            enqueueResponse(STATUS_ERROR)
            val response = propertiesApi.fetchPropertyById(listingId = Random.nextInt())
            assertTrue(response is NetworkResponse.UnknownError)
            assertNotNull(response)
        }

    @Test
    fun `Given a Properties request With network failure Then return NetworkResponse_NetworkError`() =
        runTest {
            mockWebServer.shutdown()

            val response = try {
                propertiesApi.fetchProperties()
            } catch (e: IOException) {
                NetworkResponse.NetworkError(e)
            }

            assertTrue(response is NetworkResponse.NetworkError)
        }

    @Test
    fun `Given a Property request With network failure Then return NetworkResponse_NetworkError`() =
        runTest {
            mockWebServer.shutdown()

            val response = try {
                propertiesApi.fetchPropertyById(any())
            } catch (e: IOException) {
                NetworkResponse.NetworkError(e)
            }

            assertTrue(response is NetworkResponse.NetworkError)
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
