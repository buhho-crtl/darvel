package com.adriandp.marvel.view

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.adriandp.marvel.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter(value = ["bind:url", "bind:needResize", "bind:imageRound"])
fun loadImage(view: ImageView, url: String, needResize: Boolean, imageRound: Boolean) {


    val glide = Glide.with(view)
        .load("$url.jpg")
        .placeholder(ContextCompat.getDrawable(view.context, R.drawable.load_image))

    if (needResize) {
        glide.transform(CenterCrop(), RoundedCorners(8))
    } else {
        view.layoutParams.height = 0;
        view.layoutParams.width = 0;
    }

    if (imageRound) glide.apply(RequestOptions.circleCropTransform())

    glide.into(view)
}
