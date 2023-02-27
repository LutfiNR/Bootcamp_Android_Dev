package com.bootcamp.lutfi_mealdb.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bootcamp.lutfi_mealdb.model.MealsDetail
import com.bootcamp.lutfi_mealdb.utils.Constant.TABLE_NAME_MEAL
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME_MEAL)
@Parcelize
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val listmeal: MealsDetail
    ): Parcelable