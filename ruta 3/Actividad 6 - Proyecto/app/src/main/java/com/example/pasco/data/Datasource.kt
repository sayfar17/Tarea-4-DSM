package com.example.pasco.data

import com.example.pasco.R

/**
 * An object to provide the data for the app.
 */
object Datasource {
    val coffeeShops = listOf(
        Place(R.string.coffee_shop_1_name, R.string.coffee_shop_1_description, R.drawable.cajacho),
        Place(R.string.coffee_shop_2_name, R.string.coffee_shop_2_description, R.drawable.hollybelly),
       )

    val restaurants = listOf(
        Place(R.string.restaurant_1_name, R.string.restaurant_1_description, R.drawable.soldeoro),
        Place(R.string.restaurant_2_name, R.string.restaurant_2_description, R.drawable.kimbos),
        Place(R.string.restaurant_3_name, R.string.restaurant_3_description, R.drawable.chifast)
    )

    val parks = listOf(
        Place(R.string.park_1_name, R.string.park_1_description, R.drawable.universitario),
        Place(R.string.park_2_name, R.string.park_2_description, R.drawable.comercio),
        Place(R.string.park_3_name, R.string.park_3_description, R.drawable.dac)
    )

    val museums = listOf(
        Place(R.string.museum_1_name, R.string.museum_1_description, R.drawable.museo),
        )

    val others = listOf(
        Place(R.string.mall_1_name, R.string.mall_1_description, R.drawable.tajoabierto),
        Place(R.string.mall_2_name, R.string.mall_2_description, R.drawable.cementerio),
    )
}