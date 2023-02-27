package com.bootcamp.lutfi_mealdb.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealEntity: MealEntity)

    @Query("SELECT * FROM seafood_table ORDER BY id ASC")
    fun listMeal(): Flow<List<MealEntity>>

    @Delete()
    suspend fun deleteMeal(mealEntity: MealEntity)

    @Query("DELETE FROM seafood_table")
    suspend fun deleteAllMeal()
}