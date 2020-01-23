package com.kailashdabhi.weather.data.model

import com.google.gson.annotations.SerializedName

data class Forecast(
  @SerializedName("city")
  val city: City? = null,
  @SerializedName("cnt")
  val cnt: Int=0,
  @SerializedName("cod")
  val cod: String="",
  @SerializedName("list")
  val list: List<CityWeatherDetail>,
  @SerializedName("message")
  val message: Double=0.0
)