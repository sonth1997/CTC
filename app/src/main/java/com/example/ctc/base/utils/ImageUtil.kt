package com.example.ctc.base.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import com.example.ctc.R


object ImageUtil {

    fun loadAvatarImage(context: Context,
                        url: String,
                        placeHolder: Int = R.drawable.bg_login,
                        imageView: ImageView,
                        height: Int = Target.SIZE_ORIGINAL,
                        width: Int = Target.SIZE_ORIGINAL) {
        Glide.with(context).load(url).centerCrop()
                .placeholder(placeHolder).override(height, width).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }

    fun loadImage(url: String,
                  placeHolder: Int = R.drawable.bg_login,
                  imageView: ImageView,
                  height: Int = Target.SIZE_ORIGINAL,
                  width: Int = Target.SIZE_ORIGINAL) {
        Glide.with(imageView.context).load(url).centerCrop()
                .placeholder(placeHolder).override(height, width).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
    }
    fun loadImageSpend(context: Context,
                          uri: Uri,
                          placeHolder: Int = R.drawable.ic_photo,
                          imageView: ImageView,
                          height: Int = Target.SIZE_ORIGINAL,
                          width: Int = Target.SIZE_ORIGINAL) {
        Glide.with(imageView.context).load(uri).centerCrop()
            .placeholder(placeHolder).override(height, width).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}