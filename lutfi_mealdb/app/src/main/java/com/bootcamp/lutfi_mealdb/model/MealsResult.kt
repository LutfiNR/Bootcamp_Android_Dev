package com.bootcamp.lutfi_mealdb.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MealsResult(

	@field:SerializedName("meals")
	val meals: List<MealsItem?>? = null
) : Parcelable

