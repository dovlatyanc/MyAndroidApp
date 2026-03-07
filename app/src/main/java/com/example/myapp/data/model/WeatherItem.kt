package com.example.myapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    val dt: Long,

    val main: Main,

    val weather: List<Weather>,

    @SerializedName("dt_txt")
    val dataTxt: String,
    val wind: Wind? = null,
    val clouds: Clouds? = null,
    val pop: Double? = null
)




