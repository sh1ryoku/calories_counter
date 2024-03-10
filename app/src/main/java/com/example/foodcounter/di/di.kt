package com.example.foodcounter.di

import com.example.foodcounter.presentation.screens.food_list_page.viewmodel.FoodListViewModel
import com.example.foodcounter.repository.FoodRepository
import com.google.firebase.Firebase
import com.google.firebase.database.database
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module{
    single {Firebase.database(url = "https://foodcounter-9244c-default-rtdb.europe-west1.firebasedatabase.app/").reference }
    single { FoodRepository(database = get()) }
    viewModel { FoodListViewModel(foodRepository = get())}
}