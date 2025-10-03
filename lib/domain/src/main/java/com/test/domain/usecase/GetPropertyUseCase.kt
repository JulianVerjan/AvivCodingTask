package com.test.domain.usecase

import com.test.domain.model.Property
import com.test.domain.networkresult.NetworkResult

interface GetPropertyUseCase {
    suspend operator fun invoke(parameter: Int): NetworkResult<Property>
}