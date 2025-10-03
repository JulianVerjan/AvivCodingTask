package com.test.data.api

import com.test.data.model.PropertiesDto
import com.test.data.model.PropertyDto
import com.test.network.NetworkResponse
import com.test.network.model.response.PropertiesResponse
import com.test.network.model.response.PropertyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PropertiesApi {

    @GET("listings.json")
    suspend fun fetchProperties(): NetworkResponse<PropertiesResponse, PropertiesDto>

    @GET("listings/{listingId}.json")
    suspend fun fetchPropertyById(@Path("listingId") listingId: Int): NetworkResponse<PropertyResponse, PropertyDto>
}
