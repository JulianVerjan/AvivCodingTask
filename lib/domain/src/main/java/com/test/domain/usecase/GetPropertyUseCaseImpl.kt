package com.test.domain.usecase

import com.test.domain.model.Property
import com.test.domain.networkresult.NetworkResult
import com.test.domain.repository.PropertiesRepository
import javax.inject.Inject

class GetPropertyUseCaseImpl @Inject constructor(
    private val propertyRepository: PropertiesRepository
) : GetPropertyUseCase {

    override suspend fun invoke(parameter: Int): NetworkResult<Property> =
        propertyRepository.fetchPropertyById(listingId = parameter)
}