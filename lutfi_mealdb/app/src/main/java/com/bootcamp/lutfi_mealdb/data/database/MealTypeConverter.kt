package com.bootcamp.lutfi_mealdb.data.database

import androidx.room.TypeConverter
import com.bootcamp.lutfi_mealdb.model.MealsDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealDataToString(meal: MealsDetail): String {
        return gson.toJson(meal)
    }

    @TypeConverter
    fun mealStringToData(string: String): MealsDetail {
        val listType = object : TypeToken<MealsDetail>() {}.type
        return gson.fromJson(string, listType)
    }
}