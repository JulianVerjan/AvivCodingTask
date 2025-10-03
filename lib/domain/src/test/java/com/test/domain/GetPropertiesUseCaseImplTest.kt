package com.test.domain

import com.test.domain.model.Properties
import com.test.domain.networkresult.NetworkResult
import com.test.domain.repository.PropertiesRepository
import com.test.domain.usecase.GetPropertiesUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class GetPropertiesUseCaseImplTest {

    private val propertiesRepository: PropertiesRepository = mock()
    private val mockedCodeError = 404
    private val returnMockedPropertiesResponse = mock<Properties> {
        on { items } doReturn (listOf(mock(), mock()))
    }

    @Test
    fun `Given GetPropertiesUseCaseImpl is invoked Then the response is the same as the mocked one`() =
        runTest {
            whenever(
                propertiesRepository.fetchProperties()

            ).thenReturn(NetworkResult.Success(returnMockedPropertiesResponse))

            val response = getTestSubject().invoke()
            assert(response is NetworkResult.Success)
            assertNotNull((response as NetworkResult.Success).result)
            response.result?.items?.let {
                assertTrue(it.isNotEmpty())
            }
        }

    @Test
    fun `Given GetPropertiesUseCaseImpl is invoked And the response fails Then NetworkResult Fail is returned`() =
        runTest {
            whenever(
                propertiesRepository.fetchProperties()
            )
                .thenReturn(
                    NetworkResult.Fail(
                        mockedCodeError
                    )
                )

            val response = getTestSubject().invoke()
            assertNotNull(response)
            assert(response is NetworkResult.Fail<*>)
        }

    @Test
    fun `Given GetPropertiesUseCaseImpl is invoked And the response fails Then NetworkResult Exception is returned`() =
        runTest {
            whenever(
                propertiesRepository.fetchProperties()
            )
                .thenReturn(NetworkResult.Exception(IOException()))

            val response = getTestSubject().invoke()
            assertNotNull(response)
            assert(response is NetworkResult.Exception)
        }

    private fun getTestSubject(): GetPropertiesUseCaseImpl = GetPropertiesUseCaseImpl(
        propertiesRepository = propertiesRepository
    )
}