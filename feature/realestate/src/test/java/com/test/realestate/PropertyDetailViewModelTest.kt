package com.test.realestate

import androidx.lifecycle.SavedStateHandle
import com.test.domain.model.Property
import com.test.domain.networkresult.NetworkResult
import com.test.domain.usecase.GetPropertyUseCase
import com.test.realestate.model.mapper.toPropertyUiModel
import com.test.realestate.state.PropertyDetailUiState
import com.test.realestate.viewmodel.PropertyDetailViewModel
import com.test.realestate.viewmodel.PropertyDetailViewModel.Companion.LISTING_ID_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class PropertyDetailViewModelTest {

    private val getPropertyUseCase: GetPropertyUseCase = mock()
    private val savedStateHandle = mock<SavedStateHandle>()

    val returnMockedProperty: Property = Property(
        bedrooms = Random.nextInt(),
        city = "Villers-sur-Mer",
        id = Random.nextInt(),
        area = Random.nextInt(),
        url = "https://v.seloger.com/s/crop",
        price = Random.nextInt(),
        professional = "GSL EXPLORE",
        propertyType = "Maison - Villa",
        offerType = Random.nextInt(),
        rooms = Random.nextInt()
    )

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given PropertyDetailViewModel is initialized Then UiState is Complete and response is the same as the mocked one`() =
        runTest {
            whenever(
                getPropertyUseCase.invoke(any())
            ).thenReturn(NetworkResult.Success(returnMockedProperty))

            whenever (savedStateHandle.get<Int>(LISTING_ID_KEY) ).thenReturn( 42)

            val expectedUiState: PropertyDetailUiState = PropertyDetailUiState.Complete(
                uiModel = returnMockedProperty.toPropertyUiModel()
            )
            val viewModel = getTestSubject()

            advanceUntilIdle()
            val actual = viewModel.propertyDetailStateFlow.value
            assertEquals(expectedUiState, actual)
        }

    @Test
    fun `Given PropertyDetailViewModel is initialized and fails Then UiState is Error_Connection`() =
        runTest {

            whenever(
                getPropertyUseCase.invoke(any())
            ).thenReturn(NetworkResult.Fail(errorResult = "Error"))
            whenever (savedStateHandle.get<Int>(LISTING_ID_KEY) ).thenReturn( 42)

            val expectedUiState: PropertyDetailUiState = PropertyDetailUiState.Error.Connection
            val viewModel = getTestSubject()

            advanceUntilIdle()
            val actual = viewModel.propertyDetailStateFlow.value
            assertEquals(expectedUiState, actual)
        }

    @Test
    fun `Given PropertyDetailViewModel is initialized and fails with an exception Then UiState is Error_Unknown`() =
        runTest {

            whenever(
                getPropertyUseCase.invoke(any())
            ).thenReturn(NetworkResult.Exception(Exception()))
            whenever (savedStateHandle.get<Int>(LISTING_ID_KEY) ).thenReturn( 42)

            val expectedUiState: PropertyDetailUiState = PropertyDetailUiState.Error.Unknown
            val viewModel = getTestSubject()

            advanceUntilIdle()
            val actual = viewModel.propertyDetailStateFlow.value
            assertEquals(expectedUiState, actual)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun getTestSubject(): PropertyDetailViewModel =
        PropertyDetailViewModel(savedStateHandle, getPropertyUseCase)
}
