package com.example.ctc.spend.add.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Objects(@SerializedName ("object")
                    var link : ArrayList<String>) : Parcelable