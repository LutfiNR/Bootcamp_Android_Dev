package com.bootcamp.lutfi_mealdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.lutfi_mealdb.data.RemoteDataSource
import com.bootcamp.lutfi_mealdb.data.Repository
import com.bootcamp.lutfi_mealdb.data.network.Service
import com.bootcamp.lutfi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.lutfi_mealdb.model.MealsResult
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {
    private val mealService = Service.MealService
    private val remote = RemoteDataSource(mealService)

    private val repository = Repository(remote)

    private var _listMeal: MutableLiveData<NetworkResult<MealsResult>> = MutableLiveData()
    val listMeal: LiveData<NetworkResult<MealsResult>> = _listMeal

    init {
        fetchListMeal()
    }
    private fun fetchListMeal() {
        viewModelScope.launch {
            repository.remote?.getMeal()?.collect { result ->
                _listMeal.value = result
            }
        }
    }
}