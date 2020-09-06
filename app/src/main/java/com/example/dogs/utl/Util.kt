package com.example.dogs.utl

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogs.R

val PERMISSIONS_SEND_SMS = 1

fun getProgressDrawable(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri : String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog_icon)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}


//This function is used to bing images in the layout.
@BindingAdapter("android:imageUrl")
fun loadImage(view : ImageView, url : String?){
    view.loadImage(url, getProgressDrawable(view.context))
}