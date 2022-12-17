package com.alviano.ugd_kel3.entity

import com.google.gson.annotations.SerializedName

data class ResponseCustomer(
    @SerializedName("status") val stt:String,
    val totaldata: Int,
    val data:customer
    )