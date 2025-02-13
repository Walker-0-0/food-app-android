package com.example.food_mvvm.retrofit

import com.example.easyfood.data.pojo.MealList
import com.example.food_mvvm.pojo.CategoryList
import com.example.food_mvvm.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i") id:String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String): Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategory(): Call<CategoryList>

    @GET("filter.php")
    fun getMealByCategory(@Query("c") categoryName:String): Call<MealsByCategoryList>













}