package com.kailashdabhi.weather.data.model

import com.google.gson.annotations.SerializedName

data class CityWeatherDetail(
  @SerializedName("base")
  val base: String = "",
  @SerializedName("clouds")
  val clouds: Clouds? = null,
  @SerializedName("cod")
  val cod: Int = 0,
  @SerializedName("coord")
  val coord: Coord? = null,
  @SerializedName("dt")
  val dt: Int = 0,
  @SerializedName("dt_txt")
  val dtTxt: String = "",
  @SerializedName("id")
  val id: Int = 0,
  @SerializedName("main")
  val main: Main? = null,
  @SerializedName("name")
  val name: String = "",
  @SerializedName("sys")
  val sys: Sys? = null,
  @SerializedName("visibility")
  val visibility: Int = 0,
  @SerializedName("weather")
  val weather: List<Weather>? = null,
  @SerializedName("rain")
  val rain: Rain? = null,
  @SerializedName("snow")
  val snow: Snow? = null,
  @SerializedName("wind")
  val wind: Wind? = null
) {
  class Rain

  class Snow
}