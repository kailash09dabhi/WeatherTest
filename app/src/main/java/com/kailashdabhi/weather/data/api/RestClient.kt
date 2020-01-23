package com.kailashdabhi.weather.data.api

import com.kailashdabhi.weather.BuildConfig
import com.mobimove.nursery.api.WeatherService
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.MINUTES

class RestClient @JvmOverloads constructor(url: String? = WeatherService.API_ENDPOINT) {
  private val retrofit: Retrofit
  fun weatherService(): WeatherService {
    return retrofit.create(WeatherService::class.java)
  }

  companion object {
    private val LOG_ENABLED = BuildConfig.DEBUG
  }

  init {
    val httpClientBuilder = Builder()
      .connectTimeout(1, MINUTES)
      .readTimeout(1, MINUTES)
      .writeTimeout(1, MINUTES)
    if (LOG_ENABLED) {
      val logging = HttpLoggingInterceptor()
      logging.level = BODY
      httpClientBuilder.addInterceptor(logging)
    }
    retrofit = Retrofit.Builder()
      .baseUrl(url)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(httpClientBuilder.build())
      .build()
  }
}