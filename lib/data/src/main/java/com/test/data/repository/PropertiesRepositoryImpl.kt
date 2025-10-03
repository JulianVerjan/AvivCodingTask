package com.test.data.repository

import com.test.data.api.PropertiesApi
import com.test.domain.model.Properties
import com.test.domain.model.Property
import com.test.domain.networkresult.NetworkResult
import com.test.domain.repository.PropertiesRepository
import com.test.network.model.mapper.toPropertiesDomainModel
import com.test.network.model.mapper.toPropertyDomainModel
import com.test.network.model.mapper.toRepositoryResult
import com.test.network.model.response.PropertiesResponse
import com.test.network.model.response.PropertyResponse
import javax.inject.Inject

class PropertiesRepositoryImpl @Inject constructor(
    private val propertiesApi: PropertiesApi
) : PropertiesRepository {

    override suspend fun fetchProperties(): NetworkResult<Properties> =
        propertiesApi.fetchProperties().toRepositoryResult(
            mapper = PropertiesResponse::toPropertiesDomainModel
        )

    override suspend fun fetchPropertyById(listingId: Int): NetworkResult<Property> =
        propertiesApi.fetchPropertyById(listingId).toRepositoryResult(
            mapper = PropertyResponse::toPropertyDomainModel
        )
}
