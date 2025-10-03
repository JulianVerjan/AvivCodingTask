package com.test.properties.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.properties.states.PropertiesUiState
import com.test.properties.model.mapper.toPropertyUiModelList
import com.test.domain.networkresult.NetworkResult
import com.test.domain.usecase.GetPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertiesViewModel @Inject constructor(
    private val getPropertiesUseCase: GetPropertiesUseCase
) : ViewModel() {

    private val _propertiesMutableStateFlow: MutableStateFlow<PropertiesUiState> =
        MutableStateFlow(PropertiesUiState.Loading)
    val propertiesStateFlow: StateFlow<PropertiesUiState> =
        _propertiesMutableStateFlow.asStateFlow()

    init {
        fetchProperties()
    }

    private fun fetchProperties() {
        viewModelScope.launch {
            val uiState = when (val properties = getPropertiesUseCase.invoke()) {
                is NetworkResult.Success -> {
                    PropertiesUiState.Complete(
                        uiModels = properties.result?.items.toPropertyUiModelList()
                    )
                }

                is NetworkResult.Exception -> PropertiesUiState.Error.Unknown
                is NetworkResult.Fail<*> -> PropertiesUiState.Error.Connection
            }
            _propertiesMutableStateFlow.update { uiState }
        }
    }
}