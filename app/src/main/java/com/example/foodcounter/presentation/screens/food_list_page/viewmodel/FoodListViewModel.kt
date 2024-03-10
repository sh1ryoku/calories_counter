package com.example.foodcounter.presentation.screens.food_list_page.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodcounter.data.models.Food
import com.example.foodcounter.extentions.asLiveData
import com.example.foodcounter.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodListViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _food = MutableLiveData<List<Food>>()
    val food = _food.asLiveData()
    fun getFood() {
        viewModelScope.launch {
            val foodList = foodRepository.readFoodData()
            _food.value = foodList
        }
    }
}