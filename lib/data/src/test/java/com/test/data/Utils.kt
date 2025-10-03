package com.test.data

import com.test.data.model.PropertiesDto
import com.test.data.model.PropertyDto
import com.test.domain.model.Property
import com.test.network.NetworkResponse
import com.test.network.model.mapper.toPropertyDomainModel
import com.test.network.model.response.PropertiesResponse
import com.test.network.model.response.PropertyResponse
import java.io.IOException
import kotlin.random.Random

const val ERROR_CODE = 404

val returnMockedProperties: List<PropertyResponse> = listOf(
    PropertyResponse(
        bedrooms = Random.nextInt(),
        city = "Villers-sur-Mer",
        id = Random.nextInt(),
        area = Random.nextInt(),
        url = "https://v.seloger.com/s/crop",
        price = Random.nextInt(),
        professional = "GSL EXPLORE",
        propertyType = "Maison - Villa",
        offerType = Random.nextInt(),
        rooms = Random.nextInt()
    ),
    PropertyResponse(
        bedrooms = Random.nextInt(),
        city = "Villers-sur-Mer-2",
        id = Random.nextInt(),
        area = Random.nextInt(),
        url = "https://v.seloger.com/s/crop-XX.png",
        price = Random.nextInt(),
        professional = "GSL EXPLORE RA",
        propertyType = "Flat",
        offerType = Random.nextInt(),
        rooms = Random.nextInt()
    )
)

val mockedNetworkApiErrorResponse: NetworkResponse<PropertiesResponse, PropertiesDto> =
    NetworkResponse.ApiError(
        code = ERROR_CODE,
        body = PropertiesDto(
            items = listOf(
                PropertyDto(
                    bedrooms = Random.nextInt(),
                    city = "Villers-sur-Mer",
                    id = Random.nextInt(),
                    area = Random.nextInt(),
                    url = "https://v.seloger.com/s/crop",
                    price = Random.nextInt(),
                    professional = "GSL EXPLORE",
                    propertyType = "Maison - Villa",
                    offerType = Random.nextInt(),
                    rooms = Random.nextInt()
                ),
                PropertyDto(
                    bedrooms = Random.nextInt(),
                    city = "Villers-sur-Mer-2",
                    id = Random.nextInt(),
                    area = Random.nextInt(),
                    url = "https://v.seloger.com/s/crop-XX.png",
                    price = Random.nextInt(),
                    professional = "GSL EXPLORE RA",
                    propertyType = "Flat",
                    offerType = Random.nextInt(),
                    rooms = Random.nextInt()
                )
            ),
            totalCount = Random.nextInt()
        )
    )

val mockedNetworkApiError: NetworkResponse<PropertyResponse, PropertyDto> =
    NetworkResponse.ApiError(
        code = ERROR_CODE,
        body = PropertyDto(
            bedrooms = Random.nextInt(),
            city = "Villers-sur-Mer",
            id = Random.nextInt(),
            area = Random.nextInt(),
            url = "https://v.seloger.com/s/crop",
            price = Random.nextInt(),
            professional = "GSL EXPLORE",
            propertyType = "Maison - Villa",
            offerType = Random.nextInt(),
            rooms = Random.nextInt()
        )
    )

val mockedNetworkFailResponse: NetworkResponse.NetworkError = NetworkResponse.NetworkError(
    error = IOException()
)

val returnMockedPropertyList: List<Property> = returnMockedProperties.map {
    it.toPropertyDomainModel()
}

val returnMockedPropertiesResponse: PropertiesResponse = PropertiesResponse(
    items = returnMockedProperties,
    totalCount = Random.nextInt()
)

