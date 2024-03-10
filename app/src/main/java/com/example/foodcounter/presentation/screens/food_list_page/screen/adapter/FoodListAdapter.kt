package com.example.foodcounter.presentation.screens.food_list_page.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcounter.R
import com.example.foodcounter.data.models.Food
import com.example.foodcounter.databinding.FoodCardBinding


class FoodListAdapter(private val onFoodClick: (Food) -> Unit) : ListAdapter<Food, FoodListAdapter.FoodListHolder>(FoodDiffItemCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.food_card,
            parent, false
        )
        return FoodListHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodListAdapter.FoodListHolder, position: Int) {
        getItem(position)?.let { food -> holder.bind(food) }
    }

    inner class FoodListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = FoodCardBinding.bind(itemView)
        fun bind(food: Food) {
            with(binding) {
                foodTitleTV.text = food.foodTitle
                caloriesAmountTV.text = food.calories.toString()
                proteinsAmountTV.text = food.proteins.toString()
                fatsAmountTV.text = food.fats.toString()
                carbsAmountTV.text = food.carbs.toString()
                foodCard.setOnClickListener{
                    onFoodClick(food)
                }
            }
        }
    }
}

private object FoodDiffItemCallback : DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.foodTitle == newItem.foodTitle
    }
}