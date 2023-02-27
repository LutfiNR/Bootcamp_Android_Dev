package com.bootcamp.lutfi_mealdb.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bootcamp.lutfi_mealdb.R
import com.bootcamp.lutfi_mealdb.data.database.MealEntity
import com.bootcamp.lutfi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.lutfi_mealdb.databinding.ActivityDetailBinding
import com.bootcamp.lutfi_mealdb.model.MealsDetail
import com.bootcamp.lutfi_mealdb.model.MealsDetailItem
import com.bootcamp.lutfi_mealdb.model.MealsItem
import com.bootcamp.lutfi_mealdb.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var mealDetail: MealsDetail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Detail Meal"
            setDisplayHomeAsUpEnabled(true)
        }
        val meal = intent.getParcelableExtra<MealsItem>(EXTRA_MEAL)
        detailViewModel.fetchMealDetail(meal?.idMeal)
        binding.play.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.play.tag.toString()))
            startActivity(intent)
        }


        detailViewModel.mealDetail.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> handleUi(
                    layoutWrapper = false,
                    progressBar = true,
                    errorTv = false,
                    errorImg = false
                )
                is NetworkResult.Error -> {
                    handleUi(
                        layoutWrapper = false,
                        progressBar = false,
                        errorTv = true,
                        errorImg = true
                    )
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    binding.dataDetail = result.data
                    mealDetail = result.data!!

                    handleUi(
                        layoutWrapper = true,
                        progressBar = false,
                        errorTv = false,
                        errorImg = false
                    )
                }
            }
        }

        isFavoriteMeal(meal)
    }

    private fun isFavoriteMeal(mealSelected: MealsItem?) {
        detailViewModel.favoriteMealList.observe(this) { result ->
            val meal = result.find { favorite ->
                favorite.listmeal.meals?.get(0)?.idMeal == mealSelected?.idMeal
            }
            if (meal != null) {
                binding.favIcon.apply {
                    setImageResource(R.drawable.favfill)

                    setOnClickListener {
                        deleteFavoriteMeal(meal.id)
                    }
                }
            } else {
                binding.favIcon.apply {
                    setImageResource(R.drawable.fav)
                    setOnClickListener {
                        insertFavoriteMeal()
                    }
                }
            }
        }
    }

    private fun deleteFavoriteMeal(mealEntityId: Int) {
        val mealEntity = MealEntity(mealEntityId,mealDetail)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite", Toast.LENGTH_SHORT).show()
    }

    private fun insertFavoriteMeal() {
        val mealEntity = MealEntity(listmeal = mealDetail)
        detailViewModel.insertFavoriteMeal(mealEntity)
        Log.d("cekdata","$mealEntity")
        Toast.makeText(this, "Successfully added to favorite", Toast.LENGTH_SHORT).show()
    }

    private fun handleUi(
        layoutWrapper: Boolean,
        progressBar: Boolean,
        errorTv: Boolean,
        errorImg: Boolean
    ) {
        binding.apply {
            mealDetailWrapper.isVisible = layoutWrapper
            progressbar.isVisible = progressBar
            errorIcon.isVisible = errorImg
            errorText.isVisible = errorTv
        }
    }

    companion object {
        const val EXTRA_MEAL = "meal"
    }
}