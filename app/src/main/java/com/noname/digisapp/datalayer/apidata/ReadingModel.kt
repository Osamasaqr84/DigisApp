package com.noname.digisapp.datalayer.apidata

import com.google.gson.annotations.SerializedName

data class ReadingModel(
    @SerializedName("RSRP") val RSRP: Int,
    @SerializedName("RSRQ") val RSRQ: Int,
    @SerializedName("SINR") val SINR: Int
)