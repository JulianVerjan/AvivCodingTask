package com.test.properties.model.mapper

import com.test.properties.model.PropertyUiModel
import com.test.domain.model.Property
import com.test.properties.model.PropertyDetailUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.text.NumberFormat
import java.util.Locale
import kotlin.String

val formatPattern: NumberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY)

fun List<Property>?.toPropertyUiModelList(): ImmutableList<PropertyUiModel> = this?.let {
    map {
        PropertyUiModel(
            city = it.city,
            id = it.id,
            url = it.url,
            propertyType = it.propertyType,
            price = it.price.toFormattedPrice(),
        )
    }.toImmutableList()
} ?: persistentListOf()

fun Property?.toPropertyUiModel(): PropertyDetailUiModel? = this?.let {
    PropertyDetailUiModel(
        bedrooms = it.bedrooms,
        city = it.city,
        id = it.id,
        area = it.area,
        url = it.url,
        price = it.price.toFormattedPrice(),
        professional = it.professional,
        propertyType = it.propertyType,
        offerType = it.offerType,
        rooms = it.rooms
    )
}

private fun Int.toFormattedPrice(): String = formatPattern.format(this)