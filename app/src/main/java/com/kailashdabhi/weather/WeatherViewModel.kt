package com.kailashdabhi.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kailashdabhi.weather.base.Resource
import com.kailashdabhi.weather.base.RxUtils
import com.kailashdabhi.weather.data.WeatherRepository
import com.kailashdabhi.weather.data.model.CityWeatherDetail

/**
 * @author kailash09dabhi@gmail.com
 * @date 22, Jan 2020 (16:29)
 */
class WeatherViewModel : ViewModel() {
  private val weatherList = MutableLiveData<Resource<List<CityWeatherDetail>>>()
  private val forecast = MutableLiveData<Resource<Map<String, List<CityWeatherDetail>>>>()
  fun weatherList(): LiveData<Resource<List<CityWeatherDetail>>> {
    return weatherList
  }

  fun forecast(): LiveData<Resource<Map<String, List<CityWeatherDetail>>>> {
    return forecast
  }

  fun weather(cities: String) {
    weatherList.postValue(Resource.loading(null))
    val cityList = cities.split(",").map { it.trim() }.toList()
    if (cityList.size in 3..7)
      WeatherRepository.instance.weatherOnce(cityList)
        .doOnSuccess {
          weatherList.postValue(Resource.success(it.toList() as List<CityWeatherDetail>))
        }
        .doOnError {
          weatherList.postValue(Resource.error(it.message ?: "", null))
        }
        .subscribe(RxUtils.singleObserver())
    else {
      weatherList.postValue(
        Resource.error(
          "User should enter minimum 3 cities and max 7 cities!",
          null
        )
      )
    }
  }

  fun forecast(city: String) {
    WeatherRepository.instance.forecastOnce(city)
      .doOnSubscribe { forecast.postValue(Resource.loading(null)) }
      .doOnSuccess { fc ->
        val distinct: List<String> = fc.list.map { it.dtTxt.split(" ")[0] }.distinct()
        val dateAndWeatherListMap = HashMap<String, List<CityWeatherDetail>>()
        distinct.forEach { date ->
          dateAndWeatherListMap[date] = fc.list.filter { it.dtTxt.split(" ")[0] == date }
        }
        forecast.postValue(Resource.success(dateAndWeatherListMap))
      }
      .doOnError {
        forecast.postValue(Resource.error(it.message ?: "", null))
      }
      .subscribe(RxUtils.singleObserver())
  }
}