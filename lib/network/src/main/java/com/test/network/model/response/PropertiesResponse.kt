package com.test.network.model.response

import com.squareup.moshi.Json

data class PropertiesResponse(
    @field:Json("items") val items: List<PropertyResponse>,
    @field:Json("totalCount") val totalCount: Int
)