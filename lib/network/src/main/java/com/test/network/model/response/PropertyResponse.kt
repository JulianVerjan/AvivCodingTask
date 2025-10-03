package com.test.network.model.response

import com.squareup.moshi.Json

data class PropertyResponse(
    @field:Json("bedrooms") val bedrooms: Int?,
    @field:Json("city") val city: String,
    @field:Json("id") val id: Int,
    @field:Json("area") val area: Int,
    @field:Json("url") val url: String?,
    @field:Json("price") val price: Int,
    @field:Json("professional") val professional: String,
    @field:Json("propertyType") val propertyType: String,
    @field:Json("offerType") val offerType: Int,
    @field:Json("rooms") val rooms: Int?,
)