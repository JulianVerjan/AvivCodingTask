package com.test.realestate.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.networkresult.NetworkResult
import com.test.domain.usecase.GetPropertyUseCase
import com.test.realestate.model.mapper.toPropertyUiModel
import com.test.realestate.states.PropertyDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val getPropertyUseCase: GetPropertyUseCase
) : ViewModel() {

    private val _propertyDetailMutableStateFlow: MutableStateFlow<PropertyDetailUiState> =
        MutableStateFlow(PropertyDetailUiState.Loading)
    val propertyDetailStateFlow: StateFlow<PropertyDetailUiState> =
        _propertyDetailMutableStateFlow.asStateFlow()

    init {
        fetchPropertyDetail()
    }

    private fun fetchPropertyDetail() {
        viewModelScope.launch {
            val id: Int = savedStateHandle["id"] ?: 0
            val uiState = when (val properties = getPropertyUseCase.invoke(id)) {
                is NetworkResult.Success -> {
                    PropertyDetailUiState.Complete(
                        uiModel = properties.result?.toPropertyUiModel()
                    )
                }

                is NetworkResult.Exception -> PropertyDetailUiState.Error.Unknown
                is NetworkResult.Fail<*> -> PropertyDetailUiState.Error.Connection
            }
            _propertyDetailMutableStateFlow.update { uiState }
        }
    }
}