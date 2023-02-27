package com.bootcamp.lutfi_mealdb.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bootcamp.lutfi_mealdb.adapter.FavoriteAdapter
import com.bootcamp.lutfi_mealdb.databinding.ActivityFavoriteBinding
import com.bootcamp.lutfi_mealdb.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private val favoriteMealAdapter by lazy { FavoriteAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Favorite Meal"
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel.favoriteMealList.observe(this) { result ->
            if (result.isEmpty()) {
                binding.apply {
                    rvFavoriteMeal.isVisible = false
                    emptyTv.isVisible = true
                    emptyIcon.isVisible = true
                }
            } else {
                binding.rvFavoriteMeal.apply {
                    adapter = favoriteMealAdapter
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(
                        this@FavoriteActivity,
                        2,
                        GridLayoutManager.VERTICAL,
                        false
                    )
                }

                favoriteMealAdapter.apply {
                    setData(result)
                }
            }
        }

    }

}