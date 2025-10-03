package com.test.properties

import com.test.properties.states.UIState
import com.test.properties.viewmodel.PropertiesViewModel
import com.test.domain.model.ContentPage
import com.test.domain.networkresult.NetworkResult
import com.test.domain.usecase.GetPropertiesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ContentPageViewModelTest {

    private val getContePageUseCase: GetPropertiesUseCase = mock()
    private val mockedContentPageResponse: String = "<p> Truecaller Hello World </p>"
    private val returnMockedContentPageResponse = mock<ContentPage> {
        on { getPlainText() } doReturn (mockedContentPageResponse)
    }

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given onOperationsButtonClicked is called Then uiState returns success`() = runTest {
        whenever(
            getContePageUseCase.invoke()
        ).thenReturn(NetworkResult.Success(returnMockedContentPageResponse))

        val viewModel = PropertiesViewModel(getContePageUseCase)
        viewModel.onOperationsButtonClicked()
        val job = launch {
            viewModel.viewStateFlow.collect {
                assertEquals(it.uiState, UIState.SUCCESS)
            }
        }
        job.cancel()
    }

    @Test
    fun `Given onOperationsButtonClicked is called Then fifteenCharacter is equals to mocked one`() =
        runTest {
            whenever(
                getContePageUseCase.invoke()
            ).thenReturn(NetworkResult.Success(returnMockedContentPageResponse))

            val viewModel = PropertiesViewModel(getContePageUseCase)
            viewModel.onOperationsButtonClicked()
            val job = launch {
                viewModel.viewStateFlow.collect {
                    assertEquals(' ', it.fifteenCharacter)
                }
            }
            job.cancel()
        }

    @Test
    fun `Given onOperationsButtonClicked is called Then everyFifteenCharacterList is equals to mocked one`() =
        runTest {
            whenever(
                getContePageUseCase.invoke()
            ).thenReturn(NetworkResult.Success(returnMockedContentPageResponse))

            val viewModel = PropertiesViewModel(getContePageUseCase)
            viewModel.onOperationsButtonClicked()
            val expectedValue = listOf(' ', 'p')
            val job = launch {
                viewModel.viewStateFlow.collect {
                    assertEquals(expectedValue, it.everyFifteenCharacterList)
                }
            }
            job.cancel()
        }

    @Test
    fun `Given onOperationsButtonClicked is called Then wordOccurrencesCounter is equals to mocked one`() =
        runTest {
            whenever(
                getContePageUseCase.invoke()
            ).thenReturn(NetworkResult.Success(returnMockedContentPageResponse))

            val viewModel = PropertiesViewModel(getContePageUseCase)
            viewModel.onOperationsButtonClicked()
            val expectedValue = mutableMapOf<String, Int>().let {
                it["<p>"] = 1
                it["Truecaller"] = 1
                it["Hello"] = 1
                it["World"] = 1
                it["</p>"] = 1
            }

            val job = launch {
                viewModel.viewStateFlow.collect {
                    assertEquals(expectedValue, it.everyFifteenCharacterList)
                }
            }
            job.cancel()
        }

    @Test
    fun `Given onOperationsButtonClicked is called Then uiState returns error`() = runTest {

        whenever(
            getContePageUseCase.invoke()
        ).thenReturn(NetworkResult.Fail(returnMockedContentPageResponse))

        val viewModel = PropertiesViewModel(getContePageUseCase)
        viewModel.onOperationsButtonClicked()

        val job = launch {
            viewModel.viewStateFlow.collect {
                assertEquals(it.uiState, UIState.ERROR)
            }
        }
        job.cancel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}