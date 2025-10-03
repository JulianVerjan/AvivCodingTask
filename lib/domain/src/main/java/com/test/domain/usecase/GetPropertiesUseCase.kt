package com.test.domain.usecase

import com.test.domain.model.Properties
import com.test.domain.networkresult.NetworkResult

interface GetPropertiesUseCase {
    suspend operator fun invoke(): NetworkResult<Properties>
}