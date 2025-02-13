package com.example.food_mvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food_mvvm.R
import com.example.food_mvvm.adapters.CategoryMealAdapter
import com.example.food_mvvm.databinding.ActivityCategoryMealBinding
import com.example.food_mvvm.databinding.ActivityMealBinding
import com.example.food_mvvm.fragments.HomeFragment
import com.example.food_mvvm.viewModel.CategoryMealViewModel

class CategoryMealActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealBinding
    lateinit var categoryMealViewModel: CategoryMealViewModel
    lateinit var categoryMealAdapter: CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecycleView()
        categoryMealViewModel = ViewModelProvider(this)[CategoryMealViewModel::class.java]

        categoryMealViewModel.getMealByCategories(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMealViewModel.observeMealLiveData().observe(this, {mealList ->
            binding.tvCategoryCount.text = mealList.size.toString()
            categoryMealAdapter.setMealList(mealList)

        })
    }

    private fun prepareRecycleView() {
        categoryMealAdapter = CategoryMealAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealAdapter
        }
    }
}