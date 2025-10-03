package com.test.network.model.mapper

import com.test.domain.model.Properties
import com.test.domain.model.Property
import com.test.network.model.response.PropertiesResponse
import com.test.network.model.response.PropertyResponse

fun PropertiesResponse.toPropertiesDomainModel(): Properties = Properties(
    items = this.items.map { propertyResponse ->
        propertyResponse.toPropertyDomainModel()
    },
    totalCount = this.totalCount
)

fun PropertyResponse.toPropertyDomainModel(): Property = Property(
    id = id,
    bedrooms = bedrooms,
    price = price,
    propertyType = propertyType,
    area = area,
    city = city,
    offerType = offerType,
    professional = professional,
    rooms = rooms,
    url = url
)