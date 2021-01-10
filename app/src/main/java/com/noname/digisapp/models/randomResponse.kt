package com.noname.digisapp.models

import com.google.gson.annotations.SerializedName

class RandomResponse(
    @SerializedName("RSRP") val RSRP: Int,
    @SerializedName("RSRQ") val RSRQ: Int,
    @SerializedName("SINR") val SINR: Int,
    @SerializedName("CurrentTime") var CurrentTime: Double,

    @SerializedName("RSRPColor") var RSRPColor: String,
    @SerializedName("RSRQColor") var RSRQColor: String,
    @SerializedName("SINRColor") var SINRColor: String
)