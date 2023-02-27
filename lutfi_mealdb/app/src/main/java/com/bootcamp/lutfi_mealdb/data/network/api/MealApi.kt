package com.bootcamp.lutfi_mealdb.data.network.api

import com.bootcamp.lutfi_mealdb.model.MealsDetail
import com.bootcamp.lutfi_mealdb.model.MealsResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("filter.php?c=Seafood")
    suspend fun getMealList(
    ): Response<MealsResult>

    @GET("lookup.php")
    suspend fun getMealDetail(
        @Query("i") idMeal: String?
    ): Response<MealsDetail>
}