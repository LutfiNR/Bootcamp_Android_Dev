package com.bootcamp.lutfi_mealdb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.lutfi_mealdb.model.MealsItem
import com.bootcamp.lutfi_mealdb.databinding.MealRowLayoutBinding
import com.bootcamp.lutfi_mealdb.ui.DetailActivity

class MealAdapter:RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<MealsItem>() {
        override fun areItemsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealsItem, newItem: MealsItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    inner class MealViewHolder(private val binding: MealRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealsItem) {
            binding.apply {
                data = item
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MEAL,item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(
            MealRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val itemData = differ.currentList[position]
        holder.bind(itemData)
    }
    fun setData(data: List<MealsItem?>?){
        differ.submitList(data)
    }
}