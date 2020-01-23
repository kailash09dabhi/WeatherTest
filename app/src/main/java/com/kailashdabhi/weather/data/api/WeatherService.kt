package com.mobimove.nursery.api

import com.kailashdabhi.weather.data.model.CityWeatherDetail
import com.kailashdabhi.weather.data.model.Forecast
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
  companion object {
    const val API_ENDPOINT = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "5ab67c70f8160fdf1fbd05097b4c4d2c"
  }

  @GET("weather")
  fun weatherDetailsOf(@Query("q") cityNames: String, @Query("appid") appId: String = API_KEY): Single<CityWeatherDetail>

  @GET("forecast")
  fun forecastOf(@Query("q") cityNames: String, @Query("appid") appId: String = API_KEY): Single<Forecast>

}