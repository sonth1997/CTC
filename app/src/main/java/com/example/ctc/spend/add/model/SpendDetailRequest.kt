package com.example.ctc.spend.add.model

import com.google.gson.annotations.SerializedName


data class SpendDetailRequest(@SerializedName ("title")
                              var title: String?,
                              @SerializedName ("amount")
                              var amount: Int?,
                              @SerializedName ("date_transaction")
                              var date_transaction: String?,
                              @SerializedName ("note")
                              var note: String?,
                              @SerializedName ("image")
                              var image: String?)

