package com.alexcarbonell.rickandmorty.ui.common.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.loadImage(
    imageUrl: String,
    centerCrop: Boolean = false,
    roundedCornersDp: Int? = null
) {
    val transformations = ArrayList<Transformation<Bitmap>>()
    if (centerCrop) {
        transformations.add(CenterCrop())
    }
    roundedCornersDp?.let {
        transformations.add(RoundedCorners(it.toPx(context)))
    }

    Glide
        .with(context)
        .load(imageUrl)
        .transform(*transformations.toTypedArray())
        .into(this)
}
