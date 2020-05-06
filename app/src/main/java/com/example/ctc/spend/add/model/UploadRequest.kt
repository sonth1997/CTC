package com.example.ctc.spend.add.model

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

data class UploadRequest(@SerializedName("files[]")
                         var files : MultipartBody.Part){

}