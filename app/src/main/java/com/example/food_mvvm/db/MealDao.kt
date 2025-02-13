package com.example.food_mvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.easyfood.data.pojo.Meal

@Dao
interface MealDao {

    @Insert
    suspend fun insertMeal(meal: Meal)

    @Update
    suspend fun updateMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("Select * from mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>
}