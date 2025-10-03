package com.test.domain.usecase

import com.test.domain.model.Properties
import com.test.domain.networkresult.NetworkResult
import com.test.domain.repository.PropertiesRepository
import javax.inject.Inject

class GetPropertiesUseCaseImpl @Inject constructor(
    private val propertiesRepository: PropertiesRepository
) : GetPropertiesUseCase {

    override suspend fun invoke(): NetworkResult<Properties> =
        propertiesRepository.fetchProperties()
}