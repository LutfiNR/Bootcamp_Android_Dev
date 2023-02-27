package com.bootcamp.lutfi_mealdb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class MealsDetail(
    @field:SerializedName("meals")
    val meals: List<MealsDetailItem?>? = null
):Parcelable

@Parcelize
data class MealsDetailItem(

    @field:SerializedName("idMeal")
    val idMeal: String? = null,

    @field:SerializedName("strMeal")
    val strMeal: String? = null,

    @field:SerializedName("strMealThumb")
    val strMealThumb: String? = null,

    @field:SerializedName("strArea")
    val strArea: String? = null,

    @field:SerializedName("strInstructions")
    val strInstructions: String? = null,

    @field:SerializedName("strYoutube")
    val strYoutube: String? = null,

) : Parcelable