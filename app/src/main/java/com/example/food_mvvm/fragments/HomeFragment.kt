package com.example.food_mvvm.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easyfood.data.pojo.Meal
import com.example.food_mvvm.activities.CategoryMealActivity
import com.example.food_mvvm.activities.MainActivity
import com.example.food_mvvm.activities.MealActivity
import com.example.food_mvvm.adapters.CategoriesAdapter
import com.example.food_mvvm.adapters.PopularAdapter
import com.example.food_mvvm.databinding.FragmentHomeBinding
import com.example.food_mvvm.db.MealDatabase
import com.example.food_mvvm.pojo.Category
import com.example.food_mvvm.pojo.MealsByCategory
import com.example.food_mvvm.viewModel.HomeViewModel

class HomeFragment(): Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter: PopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.food_mvvm.fragments.idMeal"
        const val MEAL_NAME = "com.example.food_mvvm.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.food_mvvm.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.food_mvvm.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = PopularAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecycleView()
        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItems()
        observePopularItems()
        onPopularItemClick()

        prepareCategoriesRecycleView()
        viewModel.getCategories()
        observeCategories()
        onCategoryClick()
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemsClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecycleView() {
        binding.recycleViewPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun prepareCategoriesRecycleView() {
        binding.recycleViewCategory.apply {
            layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observePopularItems() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,
            {mealsList ->
                popularItemsAdapter.setMeals(mealsByCategoryList = mealsList as ArrayList<MealsByCategory>)
            })
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner,
            {categories ->
                categoriesAdapter.setCategorylist(categories as ArrayList<Category>)
            })
    }









    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,
            {meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.randomMealImg)

                this.randomMeal = meal
        })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }
}