package com.bootcamp.lutfi_mealdb.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.lutfi_mealdb.data.database.MealEntity
import com.bootcamp.lutfi_mealdb.databinding.FavoriteMealRowLayoutBinding
import com.bootcamp.lutfi_mealdb.ui.FavoriteActivity
import com.bootcamp.lutfi_mealdb.ui.FavoriteDetailActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<MealEntity>() {
        override fun areItemsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MealEntity, newItem: MealEntity): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    inner class FavoriteViewHolder(private val binding: FavoriteMealRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MealEntity) {
            binding.apply {
                dataDetail = item.listmeal
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, FavoriteDetailActivity::class.java)
                    intent.putExtra(FavoriteDetailActivity.EXTRA_FAVORITE_MEAL,item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            FavoriteMealRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val itemData = differ.currentList[position]
        holder.bind(itemData)
    }

    fun setData(list: List<MealEntity?>?) {
        differ.submitList(list)
    }

}