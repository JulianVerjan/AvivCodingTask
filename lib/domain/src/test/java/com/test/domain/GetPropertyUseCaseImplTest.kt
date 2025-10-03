package com.test.domain

import com.test.domain.model.Property
import com.test.domain.networkresult.NetworkResult
import com.test.domain.repository.PropertiesRepository
import com.test.domain.usecase.GetPropertyUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException
import kotlin.random.Random

@ExperimentalCoroutinesApi
class GetPropertyUseCaseImplTest {

    private val propertyRepository: PropertiesRepository = mock()
    private val mockedCodeError = 404
    private val returnMockedProperty: Property = Property(
        bedrooms = Random.nextInt(),
        city = "Villers-sur-Mer-2",
        id = Random.nextInt(),
        area = Random.nextInt(),
        url = "https://v.seloger.com/s/crop-XX.png",
        price = Random.nextInt(),
        professional = "GSL EXPLORE RA",
        propertyType = "Flat",
        offerType = Random.nextInt(),
        rooms = Random.nextInt()
    )

    @Test
    fun `Given GetPropertyUseCaseImpl is invoked Then the response is the same as the mocked one`() =
        runTest {
            val listingId = Random.nextInt()
            whenever(
                propertyRepository.fetchPropertyById(listingId = listingId)

            ).thenReturn(NetworkResult.Success(returnMockedProperty))

            val response = getTestSubject().invoke(parameter = listingId)
            assert(response is NetworkResult.Success)
            assertNotNull((response as NetworkResult.Success).result)
        }

    @Test
    fun `Given GetPropertyUseCaseImpl is invoked And the response fails Then NetworkResult Fail is returned`() =
        runTest {
            val listingId = Random.nextInt()
            whenever(
                propertyRepository.fetchPropertyById(listingId)
            )
                .thenReturn(
                    NetworkResult.Fail(
                        mockedCodeError
                    )
                )

            val response = getTestSubject().invoke(parameter = listingId)
            assertNotNull(response)
            assert(response is NetworkResult.Fail<*>)
        }

    @Test
    fun `Given GetPropertyUseCaseImpl is invoked And the response fails Then NetworkResult Exception is returned`() =
        runTest {
            val listingId = Random.nextInt()
            whenever(
                propertyRepository.fetchPropertyById(listingId)
            )
                .thenReturn(NetworkResult.Exception(IOException()))

            val response = getTestSubject().invoke(parameter = listingId)
            assertNotNull(response)
            assert(response is NetworkResult.Exception)
        }

    private fun getTestSubject(): GetPropertyUseCaseImpl = GetPropertyUseCaseImpl(
        propertyRepository = propertyRepository
    )
}