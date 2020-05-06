package com.example.ctc.extensions

import com.example.ctc.Config

fun String.toImageUrl(): String {
    return Config.IMAGE_URL + this
}