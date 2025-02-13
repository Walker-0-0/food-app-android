package com.example.food_mvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.data.pojo.Meal
import com.example.food_mvvm.R
import com.example.food_mvvm.databinding.ActivityMealBinding
import com.example.food_mvvm.db.MealDatabase
import com.example.food_mvvm.fragments.HomeFragment
import com.example.food_mvvm.viewModel.MealViewModel
import com.example.food_mvvm.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealID: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel
    private var mealToSave:Meal?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDataBase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDataBase)

        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformation()

        loading()
        mealMvvm.getMealDetail(mealID)
        observeMealDetail()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorite.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeMealDetail() {
        mealMvvm.observeMealDetailLiveData().observe(this, object: Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponse()
                val meal = t
                mealToSave = meal

                binding.tvCategory.text = "Category" +  meal!!.strCategory
                binding.tvLocation.text = "Location" +  meal!!.strArea
                binding.tvInstructionContent.text = meal.strInstructions
            }

        })
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealID = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setInformation() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun loading() {
        binding.btnAddToFavorite.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvLocation.visibility = View.INVISIBLE
        binding.btnAddToFavorite.visibility = View.INVISIBLE
    }

    private fun onResponse() {
        binding.btnAddToFavorite.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvLocation.visibility = View.VISIBLE
        binding.btnAddToFavorite.visibility = View.VISIBLE
    }
}