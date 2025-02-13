package com.example.food_mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfood.data.pojo.Meal
import com.example.easyfood.data.pojo.MealList
import com.example.food_mvvm.pojo.CategoryList
import com.example.food_mvvm.pojo.MealsByCategory
import com.example.food_mvvm.pojo.MealsByCategoryList
import com.example.food_mvvm.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealViewModel(): ViewModel() {
    val mealLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealByCategories(categoryName: String) {
        RetrofitInstance.api.getMealByCategory(categoryName).enqueue(object: Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                response.body()?.let { mealList ->
                    mealLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("CategoryMealViewModel", t.message.toString())
            }

        })
    }

    fun observeMealLiveData(): LiveData<List<MealsByCategory>> {
        return mealLiveData
    }

}