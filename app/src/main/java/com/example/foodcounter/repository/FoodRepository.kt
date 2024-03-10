package com.example.foodcounter.repository

import com.example.foodcounter.data.models.Food
import com.example.foodcounter.data.models.FoodDTO
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class FoodRepository(
    private val database: DatabaseReference
) {
    fun writeNewFood(
        foodTitle: String, calories: Double, proteins: Double, fats: Double, carbs: Double
    ) {
        val food = FoodDTO(foodTitle, calories, proteins, fats, carbs)

        database.child("food").push().setValue(food)
    }

    suspend fun readFoodData(): List<Food> {
        val response = database.child("food").get().await()
        if (response.hasChildren()) {
            val domainList = mutableListOf<Food>()
            for (snapshot in response.children) {
                val dto = snapshot.getValue(FoodDTO::class.java)
                val id = snapshot.key ?: ""
                dto?.let { domainList.add(it.toDomain(id)) }
            }
            return domainList
        }
        return emptyList()
    }

    fun updateFood(
        key: String,
        foodTitle: String,
        calories: Double?,
        proteins: Double?,
        fats: Double?,
        carbs: Double?
    ) {
        val food = FoodDTO(foodTitle, calories, proteins, fats, carbs)
        val foodValues = food.toMap()
        val childUpdates = hashMapOf<String, Any?>(
            "food/$key" to foodValues
        )
        database.updateChildren(childUpdates)
    }

    fun deleteFood(key: String) {
        val deleteMap = hashMapOf<String, Any?>(
            "/food/$key" to null
        )
        database.updateChildren(deleteMap)
    }
}