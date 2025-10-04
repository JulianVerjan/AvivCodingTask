package com.test.realestate

sealed class ScreenRoute(val route: String) {
    object PropertiesScreenRoute: ScreenRoute("properties_screen")
    object PropertyDetailScreenRoute: ScreenRoute("property_detail_screen/{listingId}"){
        fun createRoute(listingId: Int) = "property_detail_screen/$listingId"
    }
}
