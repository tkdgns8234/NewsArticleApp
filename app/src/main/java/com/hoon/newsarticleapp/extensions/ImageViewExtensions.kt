package com.hoon.newsarticleapp.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hoon.newsarticleapp.R


/**
 * glide 로 imageView 이미지 세팅
 */

fun ImageView.setImageWithGlide(
    context: Context,
    imageUrl: String,
) {

    Glide
        .with(context)
        .load(imageUrl)
        .error(R.drawable.ic_baseline_image_24)
        .transition(GenericTransitionOptions.with(R.anim.anim_alpha))
        .transform(CenterInside(),RoundedCorners(24))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}