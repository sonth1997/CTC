package com.example.ctc.utils.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

internal fun ImageView.loadImages(url: String?) {
    Glide.with(this)
        .load(url?: "")
        .centerCrop()
        .into(this)
}