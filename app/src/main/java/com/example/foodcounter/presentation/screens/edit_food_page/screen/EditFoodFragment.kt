package com.example.foodcounter.presentation.screens.edit_food_page.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.foodcounter.data.models.Food
import com.example.foodcounter.databinding.FragmentEditFoodBinding
import com.example.foodcounter.presentation.screens.food_list_page.screen.FoodListFragment.Companion.FOOD_KEY
import com.example.foodcounter.repository.FoodRepository
import org.koin.android.ext.android.inject


class EditFoodFragment : Fragment() {

    private val foodRepository: FoodRepository by inject()
    private var currentFoodItem: Food? = null
    private lateinit var binding: FragmentEditFoodBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditFoodBinding.inflate(layoutInflater)
        val args = arguments
        currentFoodItem = args?.getSerializable(FOOD_KEY) as? Food
        setUpUi(currentFoodItem)
        return binding.root
    }

    private fun setUpUi(currentFoodItem: Food?) {
        if (currentFoodItem != null) {
            setTextViewValues(currentFoodItem)
        }
        binding.applyBTN.setOnClickListener {
            if (currentFoodItem == null) {
                onApplyButtonClick()
            } else {
                onEditClick(currentFoodItem)
            }
        }
        binding.deleteBTN.setOnClickListener { onDeleteButtonClick(currentFoodItem) }
    }

    private fun setTextViewValues(currentFoodItem: Food) {
        binding.foodTitleET.setText(currentFoodItem.foodTitle)
        binding.caloriesET.setText(currentFoodItem.calories.toString())
        binding.proteinsET.setText(currentFoodItem.proteins.toString())
        binding.fatsET.setText(currentFoodItem.fats.toString())
        binding.carbsET.setText(currentFoodItem.carbs.toString())
    }

    private fun onApplyButtonClick() {
        val foodTitle = binding.foodTitleET.text.toString()
        val calories = binding.caloriesET.text.toString().toDoubleOrNull()
        val proteins = binding.proteinsET.text.toString().toDoubleOrNull()
        val fats = binding.fatsET.text.toString().toDoubleOrNull()
        val carbs = binding.carbsET.text.toString().toDoubleOrNull()

        if (foodTitle.isNotEmpty() && calories != null && proteins != null && fats != null && carbs != null) {
            foodRepository.writeNewFood(foodTitle, calories, proteins, fats, carbs)
            findNavController().popBackStack()
        } else {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun onEditClick(currentFoodItem: Food) {
        val foodTitle = binding.foodTitleET.text.toString()
        val calories = binding.caloriesET.text.toString().toDoubleOrNull()
        val proteins = binding.proteinsET.text.toString().toDoubleOrNull()
        val fats = binding.fatsET.text.toString().toDoubleOrNull()
        val carbs = binding.carbsET.text.toString().toDoubleOrNull()

        val key = currentFoodItem.foodId
        foodRepository.updateFood(key, foodTitle, calories, proteins, fats, carbs)
        findNavController().popBackStack()
    }

    private fun onDeleteButtonClick(currentFoodItem: Food?) {
        if (currentFoodItem != null) {
            val foodTitle = currentFoodItem.foodTitle
            val key = currentFoodItem.foodId
            foodRepository.deleteFood(key)
            findNavController().popBackStack()
            Toast.makeText(context, "$foodTitle was deleted.", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                context,
                "Seems like there is nothing to delete",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
}