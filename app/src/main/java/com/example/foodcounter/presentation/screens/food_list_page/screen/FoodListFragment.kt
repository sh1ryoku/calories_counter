package com.example.foodcounter.presentation.screens.food_list_page.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.foodcounter.R
import com.example.foodcounter.data.models.Food
import com.example.foodcounter.data.models.FoodDTO
import com.example.foodcounter.databinding.FragmentFoodListBinding
import com.example.foodcounter.presentation.screens.food_list_page.screen.adapter.FoodListAdapter
import com.example.foodcounter.presentation.screens.food_list_page.viewmodel.FoodListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FoodListFragment : Fragment() {

    private val viewModel by viewModel<FoodListViewModel>()
    private lateinit var binding: FragmentFoodListBinding
    private var navController: NavController? = null
    private val adapter = FoodListAdapter(onFoodClick = { food -> onFoodClick(food) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodListBinding.inflate(layoutInflater)
        navController = findNavController()
        setUpUi()
        setUpObserve()
        return binding.root
    }

    private fun setUpUi() {
        binding.rvFoodList.adapter = adapter
        viewModel.getFood()
        binding.addFoodFAB.setOnClickListener() {
            navController?.navigate(R.id.action_foodListFragment_to_editFoodFragment)
        }
    }



    private fun setUpObserve(){
        viewModel.food.observe(viewLifecycleOwner) {food ->
            lifecycleScope.launch {
                adapter.submitList(food)
            }
        }
    }

    private fun onFoodClick(food: Food) {
        val bundle = Bundle().apply {
            putSerializable(FOOD_KEY, food)
        }
        navController?.navigate(
            R.id.action_foodListFragment_to_editFoodFragment,
            args = bundle
        )
    }

    companion object{
        const val FOOD_KEY = "food key"
    }
}
