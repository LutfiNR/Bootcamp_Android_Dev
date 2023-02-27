package com.bootcamp.lutfi_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.bootcamp.lutfi_mealdb.adapter.MealAdapter
import com.bootcamp.lutfi_mealdb.data.network.handler.NetworkResult
import com.bootcamp.lutfi_mealdb.databinding.ActivityMainBinding
import com.bootcamp.lutfi_mealdb.model.MealsItem
import com.bootcamp.lutfi_mealdb.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val mealAdapter by lazy { MealAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(null)

        binding.favIcon.setOnClickListener(){
            val intent = Intent(this@MainActivity,FavoriteActivity::class.java)
            startActivity(intent)
        }

        mainViewModel.listMeal.observe(this){result ->
            when(result){
                is NetworkResult.Loading -> {
                    handleUi(
                        recyclerView = false,
                        progressBar = true,
                        errorTv = false
                    )
                }
                is NetworkResult.Error -> {
                    handleUi(
                        recyclerView = false,
                        progressBar = false,
                        errorTv = true
                    )
                    Toast.makeText(this,result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Success -> {
                    binding.rvMeal.apply {
                        adapter = mealAdapter
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(
                            this@MainActivity,
                            2,
                            GridLayoutManager.VERTICAL,
                            false
                        )
                        mealAdapter.setData(result.data?.meals)
                    }
                    handleUi(
                        recyclerView = true,
                        progressBar = false,
                        errorTv = false
                    )
                }
            }
        }
    }
    private fun handleUi(
        recyclerView: Boolean,
        progressBar: Boolean,
        errorTv: Boolean,
    ) {
        binding.apply {
            rvMeal.isVisible = recyclerView
            progressbar.isVisible = progressBar
            errorText.isVisible = errorTv
        }
    }
}