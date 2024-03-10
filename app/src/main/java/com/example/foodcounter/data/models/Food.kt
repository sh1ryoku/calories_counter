package com.example.foodcounter.data.models

import java.io.Serializable

data class Food(
    val foodId: String,
    val foodTitle: String,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbs: Double
) : Serializable