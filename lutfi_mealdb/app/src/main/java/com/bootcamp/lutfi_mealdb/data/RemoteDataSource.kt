package com.bootcamp.lutfi_mealdb.data

import android.util.Log
import com.bootcamp.lutfi_mealdb.data.network.api.MealApi
import com.bootcamp.lutfi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.lutfi_mealdb.model.MealsDetail
import com.bootcamp.lutfi_mealdb.model.MealsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val mealApi: MealApi) {
    suspend fun getMeal(): Flow<NetworkResult<MealsResult>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val meals = mealApi.getMealList()

            if (meals.isSuccessful) {
                val responseBody = meals.body()

                if (responseBody?.meals?.isEmpty() == true) {
                    emit(NetworkResult.Error("List of Meal not found"))
                } else {
                    emit(NetworkResult.Success(responseBody!!))
                }
            } else {
                Log.d("apiServiceError", "statusCode:${meals.code()}, message:${meals.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMealDetailById(idMeal: String?): Flow<NetworkResult<MealsDetail>> = flow {
        try {
            emit(NetworkResult.Loading(true))
            val mealsDetail = mealApi.getMealDetail(idMeal)

            if (mealsDetail.isSuccessful) {
                val responseBody = mealsDetail.body()

                if (responseBody != null) {
                    emit(NetworkResult.Success(responseBody!!))
                } else {
                    emit(NetworkResult.Error("Can't fetch detail of meal."))
                }
            } else {
                Log.d("apiServiceError", "statusCode:${mealsDetail.code()}, message:${mealsDetail.message()}")
                emit(NetworkResult.Error("Failed to fetch data from server."))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("remoteError", "${e.message}")
            emit(NetworkResult.Error("Something went wrong. Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}