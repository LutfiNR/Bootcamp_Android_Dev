package com.bootcamp.lutfi_mealdb.ui

import FavoriteDetailViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.lutfi_mealdb.R
import com.bootcamp.lutfi_mealdb.data.database.MealEntity
import com.bootcamp.lutfi_mealdb.databinding.FavoriteActivityDetailBinding

class FavoriteDetailActivity : AppCompatActivity() {
    private lateinit var binding: FavoriteActivityDetailBinding
    private val favoriteDetailViewModel by viewModels<FavoriteDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavoriteActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorite Meal Detail"
            setDisplayHomeAsUpEnabled(true)
        }

        val favoriteMeal = intent.getParcelableExtra<MealEntity>(EXTRA_FAVORITE_MEAL)
        binding.dataDetail = favoriteMeal!!.listmeal


        binding.favFavIcon.apply {
            setImageResource(R.drawable.favfill)
            setOnClickListener(){
                deleteFavoriteMeal(favoriteMeal)
                val intent = Intent(binding.favFavIcon.context, FavoriteActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        binding.favPlay.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.favPlay.tag.toString()))
            startActivity(intent)
        }
    }



    private fun deleteFavoriteMeal(mealEntity: MealEntity) {
        favoriteDetailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_FAVORITE_MEAL = "favorite_meal"
    }
}