package com.kailashdabhi.weather.data

import com.kailashdabhi.weather.ServiceLocator
import com.kailashdabhi.weather.base.RxUtils
import com.kailashdabhi.weather.data.model.CityWeatherDetail
import com.kailashdabhi.weather.data.model.Forecast
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author kailash09dabhi@gmail.com
 * @date 21, Jan 2020 (17:42)
 */
open class WeatherRepository {
  private constructor()

  companion object {
    val instance: WeatherRepository =
      WeatherRepository()
  }

  fun weatherOnce(cityList: List<String>): Single<Array<out Any>> {
    val singleList: ArrayList<Single<CityWeatherDetail>> = arrayListOf()
    cityList.forEach {
      singleList.add(ServiceLocator.weatherService()!!.weatherDetailsOf(it).subscribeOn(Schedulers.newThread()))
    }
    return Single.zip(singleList) { t: Array<out Any> -> t }
      .compose(RxUtils.applyNetworkSchedulers())
  }

  fun forecastOnce(city: String): Single<Forecast> {
    return ServiceLocator.weatherService()!!.forecastOf(city)
      .compose(RxUtils.applyNetworkSchedulers())
  }
}