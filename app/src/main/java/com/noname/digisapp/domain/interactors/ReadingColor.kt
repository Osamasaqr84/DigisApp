package com.noname.digisapp.domain.interactors

import com.google.gson.annotations.SerializedName

data class ReadingColor(
    val Color: String,
    val From: String,
    val To: String
)


data class jsonParse(
    @SerializedName("RSRP") val rsrpArray: Array<ReadingColor>,
    @SerializedName("RSRQ") val sinrArray: Array<ReadingColor>,
    @SerializedName("SINR") val rsrqArray: Array<ReadingColor>
)