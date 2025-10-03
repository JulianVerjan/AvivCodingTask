package com.test.data.repositories

import com.test.data.api.PropertiesApi
import com.test.data.mockedNetworkApiError
import com.test.data.mockedNetworkApiErrorResponse
import com.test.data.mockedNetworkFailResponse
import com.test.data.repository.PropertiesRepositoryImpl
import com.test.data.returnMockedProperties
import com.test.data.returnMockedPropertiesResponse
import com.test.data.returnMockedPropertyList
import com.test.domain.networkresult.NetworkResult
import com.test.network.NetworkResponse
import com.test.network.model.mapper.toPropertyDomainModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PropertiesRepositoryImplTest {

    private val propertiesApi: PropertiesApi = mock()
    private val propertiesRepositoryImpl = PropertiesRepositoryImpl(propertiesApi)

    @Test
    fun `Given fetchProperties is called Then the content is the same as the mocked one`() =
        runTest {
            whenever(
                propertiesApi.fetchProperties()
            ).thenReturn(NetworkResponse.Success(returnMockedPropertiesResponse))

            val response = propertiesRepositoryImpl.fetchProperties()
            assert(response is NetworkResult.Success)
            assertNotNull((response as NetworkResult.Success).result)
            response.result?.items?.let {
                assertTrue(it.isNotEmpty())
            }

            assertEquals(
                expected = returnMockedPropertyList,
                actual = response.result?.items
            )
        }

    @Test
    fun `Given fetchProperties is called And the response fails Then NetworkResult Fail is returned`() =
        runTest {
            whenever(propertiesApi.fetchProperties())
                .thenReturn(mockedNetworkApiErrorResponse)
            val response = propertiesRepositoryImpl.fetchProperties()

            assertNotNull(response)
            assert(response is NetworkResult.Fail<*>)
            assertEquals(mockedNetworkApiErrorResponse, (response as NetworkResult.Fail<*>).errorResult)
        }

    @Test
    fun `Given fetchProperties is called And the response fails Then NetworkResult Exception is returned`() =
        runTest {
            whenever(propertiesApi.fetchProperties())
                .thenReturn(mockedNetworkFailResponse)
            val response = propertiesRepositoryImpl.fetchProperties()

            assertNotNull(response)
            assert(response is NetworkResult.Exception)
            assertEquals(mockedNetworkFailResponse.error, (response as NetworkResult.Exception).error)
        }

    @Test
    fun `Given fetchPropertyById is called Then the content is the same as the mocked one`() =
        runTest {
            val listingId = Random.nextInt()
            val expected = returnMockedProperties.random()
            whenever(
                propertiesApi.fetchPropertyById(listingId = listingId)
            ).thenReturn(NetworkResponse.Success(expected))

            val response = propertiesRepositoryImpl.fetchPropertyById(listingId)
            assert(response is NetworkResult.Success)
            assertNotNull((response as NetworkResult.Success).result)
            assertEquals(
                expected = expected.toPropertyDomainModel(),
                actual = response.result
            )
        }

    @Test
    fun `Given fetchPropertyById is called And the response fails Then NetworkResult Fail is returned`() =
        runTest {
            val listingId = Random.nextInt()
            whenever(propertiesApi.fetchPropertyById(listingId = listingId))
                .thenReturn(mockedNetworkApiError)
            val actualResponse = propertiesRepositoryImpl.fetchPropertyById(listingId)

            assertNotNull(actualResponse)
            assert(actualResponse is NetworkResult.Fail<*>)
            assertEquals(mockedNetworkApiError, (actualResponse as NetworkResult.Fail<*>).errorResult)
        }

    @Test
    fun `Given fetchPropertyById is called And the response fails Then NetworkResult Exception is returned`() =
        runTest {
            val listingId = Random.nextInt()
            whenever(propertiesApi.fetchPropertyById(listingId = listingId))
                .thenReturn(mockedNetworkFailResponse)
            val response = propertiesRepositoryImpl.fetchPropertyById(listingId)

            assertNotNull(response)
            assert(response is NetworkResult.Exception)
            assertEquals(mockedNetworkFailResponse.error, (response as NetworkResult.Exception).error)
        }
}
