package com.noname.digisapp.presentation.model

import com.google.gson.annotations.SerializedName

data class GsonParse(
    @SerializedName("RSRP") val rsrpArray: Array<ReadingColor>,
    @SerializedName("RSRQ") val sinrArray: Array<ReadingColor>,
    @SerializedName("SINR") val rsrqArray: Array<ReadingColor>
)

data class ReadingColor(
    val Color: String,
    var From: String,
    var To: String
)