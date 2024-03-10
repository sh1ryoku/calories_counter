package com.example.foodcounter.data.models

import java.io.Serializable

data class FoodDTO(
    val foodTitle: String? = "",
    val calories: Double? = 0.0,
    val proteins: Double? = 0.0,
    val fats: Double? = 0.0,
    val carbs: Double? = 0.0
) : Serializable {
    fun toDomain(foodId: String): Food {
        if (foodTitle == null || calories == null || proteins == null || fats == null || carbs == null) throw IllegalStateException(
            "null"
        )
        return Food(
            foodId = foodId,
            foodTitle = foodTitle,
            calories = calories,
            proteins = proteins,
            fats = fats,
            carbs = carbs
        )
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "foodTitle" to foodTitle,
            "calories" to calories,
            "proteins" to proteins,
            "fats" to fats,
            "carbs" to carbs
        )
    }
}
