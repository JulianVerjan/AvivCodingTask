package com.test.realestate

import com.test.domain.model.Properties
import com.test.domain.model.Property
import com.test.domain.networkresult.NetworkResult
import com.test.domain.usecase.GetPropertiesUseCase
import com.test.realestate.model.mapper.toPropertyUiModelList
import com.test.realestate.state.PropertiesUiState
import com.test.realestate.viewmodel.PropertiesViewModel
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
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class PropertiesViewModelTest {

    private val getPropertiesUseCase: GetPropertiesUseCase = mock()
    val returnMockedPropertyList: List<Property> = listOf(
        Property(
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
        ),
        Property(
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
    )

    val returnMockedProperties: Properties = Properties(
        items = returnMockedPropertyList,
        totalCount = Random.nextInt()
    )

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `Given PropertiesViewModel is initialized Then UiState is Complete and response is the same as the mocked one`() =
        runTest {
            whenever(
                getPropertiesUseCase.invoke()
            ).thenReturn(NetworkResult.Success(returnMockedProperties))

            val expectedUiState: PropertiesUiState = PropertiesUiState.Complete(
                uiModels = returnMockedPropertyList.toPropertyUiModelList()
            )
            val viewModel = getTestSubject()

            advanceUntilIdle()
            val actual = viewModel.propertiesStateFlow.value
            assertEquals(expectedUiState, actual)
        }

    @Test
    fun `Given PropertiesViewModel is initialized and fails Then UiState is Error_Connection`() = runTest {

        whenever(
            getPropertiesUseCase.invoke()
        ).thenReturn(NetworkResult.Fail(errorResult = "Error"))

        val expectedUiState: PropertiesUiState = PropertiesUiState.Error.Connection
        val viewModel = getTestSubject()

        advanceUntilIdle()
        val actual = viewModel.propertiesStateFlow.value
        assertEquals(expectedUiState, actual)
    }

    @Test
    fun `Given PropertiesViewModel is initialized and fails with an exception Then UiState is Error_Unknown`() = runTest {

        whenever(
            getPropertiesUseCase.invoke()
        ).thenReturn(NetworkResult.Exception(Exception()))

        val expectedUiState: PropertiesUiState = PropertiesUiState.Error.Unknown
        val viewModel = getTestSubject()

        advanceUntilIdle()
        val actual = viewModel.propertiesStateFlow.value
        assertEquals(expectedUiState, actual)


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun getTestSubject(): PropertiesViewModel = PropertiesViewModel(getPropertiesUseCase)
}