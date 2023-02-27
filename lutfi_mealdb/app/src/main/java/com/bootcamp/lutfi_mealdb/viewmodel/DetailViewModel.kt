package com.bootcamp.lutfi_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.bootcamp.lutfi_mealdb.data.LocalDataSource
import com.bootcamp.lutfi_mealdb.data.RemoteDataSource
import com.bootcamp.lutfi_mealdb.data.Repository
import com.bootcamp.lutfi_mealdb.data.database.Meal
import com.bootcamp.lutfi_mealdb.data.database.MealEntity
import com.bootcamp.lutfi_mealdb.data.network.Service
import com.bootcamp.lutfi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.lutfi_mealdb.model.MealsDetail
import com.bootcamp.lutfi_mealdb.model.MealsDetailItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {


    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    private val mealDao = Meal.getDatabase(application).MealDao()
    private val local = LocalDataSource(mealDao)

    private val repository = Repository(remote,local)

    private var _mealDetail: MutableLiveData<NetworkResult<MealsDetail>> = MutableLiveData()
    val mealDetail: LiveData<NetworkResult<MealsDetail>> = _mealDetail


    fun fetchMealDetail(idMeal: String?) {
        viewModelScope.launch {
            repository.remote!!.getMealDetailById(idMeal).collect { result ->
                _mealDetail.value = result
            }
        }
    }

    val favoriteMealList: LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()
    fun insertFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.insertMeal(mealEntity)
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }




}