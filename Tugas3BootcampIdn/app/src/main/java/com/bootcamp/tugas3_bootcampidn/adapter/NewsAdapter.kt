package com.bootcamp.tugas3_bootcampidn.adapter

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.tugas3_bootcampidn.DetailNewsActivity
import com.bootcamp.tugas3_bootcampidn.R
import com.bootcamp.tugas3_bootcampidn.databinding.ItemRowNewsBinding
import com.bootcamp.tugas3_bootcampidn.model.ArticlesItem
import com.bumptech.glide.Glide
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsAdapter(): RecyclerView.Adapter<NewsAdapter.Myviewholder>() {

    private var dataNews: List<ArticlesItem> = listOf()

    inner class Myviewholder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = ItemRowNewsBinding.bind(view)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(news: ArticlesItem){
            binding.apply {
                tvJudul.text = news.title
                tvPenulis.text = news.author
                var dateTimeString = news.publishedAt
                var date = LocalDate.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)
                tvTanggalPosting.text = date.toString()
                Glide.with(imgNews)
                    .load(news.urlToImage)
                    .error(R.drawable.ic_launcher_background)
                    .into(imgNews)
                binding.cardNews.setOnClickListener {
                    // TODO code intent here
                    val intent = Intent(itemView.context, DetailNewsActivity::class.java)
                    intent.putExtra(DetailNewsActivity.EXTRA_NEWS,dataNews[position])
                    itemView.context.startActivity(intent)
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_news,parent, false)
        return Myviewholder(view)
    }

    override fun getItemCount(): Int {
        return dataNews.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Myviewholder, position: Int) {
        holder.bind(dataNews[position])
    }


    fun setData(data: List<ArticlesItem>){
        dataNews = data
        notifyDataSetChanged()
    }
}
