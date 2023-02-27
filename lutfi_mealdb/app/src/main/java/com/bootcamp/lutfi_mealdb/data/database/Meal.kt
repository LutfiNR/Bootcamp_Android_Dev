package com.bootcamp.lutfi_mealdb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MealTypeConverter::class)
abstract class Meal : RoomDatabase() {
    abstract fun MealDao(): MealDao

    companion object {
        @Volatile
        private var instance: Meal? = null

        fun getDatabase(ctx: Context): Meal {
            val tempInstance = instance
            if (tempInstance != null) {
                instance = tempInstance
            }

            synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    ctx.applicationContext, Meal::class.java,
                    "seafood_table"
                ).build()

                instance = newInstance
                return newInstance
            }
        }
    }


}