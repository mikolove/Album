package com.mikolove.album

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter(value = ["imageUrl","progressBar"])
fun bindImage(view: ImageView, imgUrl: String?, progressBar : ProgressBar) {
    imgUrl?.let {
        Picasso.get()
            .load(imgUrl)
            .error(R.drawable.ic_broken_image_black)
            .into(view, object : Callback{
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                    view.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    progressBar.visibility = View.GONE
                    view.visibility = View.VISIBLE
                }
            })
    }
}