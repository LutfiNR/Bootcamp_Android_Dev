package com.bootcamp.lutfi_mealdb.mealbindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bootcamp.lutfi_mealdb.R
import com.bumptech.glide.Glide

object BindingAdapter {
    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        val placeHolderDrawable = R.drawable.img_placeholder
        Glide.with(imageView.context).load(imageUrl).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }
}