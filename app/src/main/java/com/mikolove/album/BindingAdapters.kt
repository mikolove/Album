package com.mikolove.album

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, imgUrl: String) {
    imgUrl?.let {
        Glide.with(view.context)
            .load(imgUrl)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image_black)
            .into(view)
    }
}