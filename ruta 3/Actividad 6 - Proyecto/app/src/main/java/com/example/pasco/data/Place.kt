package com.example.pasco.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * A data class to represent a place with its name, description, and image.
 */
data class Place(
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val image: Int
)