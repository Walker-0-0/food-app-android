package com.example.food_mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_mvvm.databinding.MealItemBinding
import com.example.food_mvvm.pojo.MealsByCategory

class CategoryMealAdapter: RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewModel>() {
    private  var mealList = ArrayList<MealsByCategory>()

    fun setMealList(mealList:List<MealsByCategory>) {
        this.mealList = mealList as ArrayList<MealsByCategory>
        notifyDataSetChanged()

    }

    inner class CategoryMealViewModel(val binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewModel {
       return CategoryMealViewModel((
               MealItemBinding.inflate(LayoutInflater.from(parent.context))
               ))
    }

    override fun onBindViewHolder(holder: CategoryMealViewModel, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal
    }

    override fun getItemCount(): Int {
       return mealList.size
    }
}