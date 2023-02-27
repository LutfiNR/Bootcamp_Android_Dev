package com.bootcamp.lutfi_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bootcamp.lutfi_mealdb.data.LocalDataSource
import com.bootcamp.lutfi_mealdb.data.Repository
import com.bootcamp.lutfi_mealdb.data.database.Meal
import com.bootcamp.lutfi_mealdb.data.database.MealEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    // LOCAL
    private val mealDao = Meal.getDatabase(application).MealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(local = local)

    val favoriteMealList: LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()
}