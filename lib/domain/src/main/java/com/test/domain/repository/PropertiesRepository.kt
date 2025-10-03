package com.test.domain.repository

import com.test.domain.model.Properties
import com.test.domain.model.Property
import com.test.domain.networkresult.NetworkResult

interface PropertiesRepository {
    suspend fun fetchProperties(): NetworkResult<Properties>
    suspend fun fetchPropertyById(listingId: Int): NetworkResult<Property>
}