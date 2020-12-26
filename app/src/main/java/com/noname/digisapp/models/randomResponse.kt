package com.noname.digisapp.models

import com.google.gson.annotations.SerializedName

class RandomResponse(
    @SerializedName("RSRP") val RSRP: Int,
    @SerializedName("RSRQ") val RSRQ: Int,
    @SerializedName("SINR") val SINR: Int
)