package com.kailashdabhi.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kailashdabhi.weather.base.Status.ERROR
import com.kailashdabhi.weather.data.model.CityWeatherDetail
import com.kailashdabhi.weather.data.model.Forecast
import com.mobimove.nursery.api.WeatherService
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * @author kailash09dabhi@gmail.com
 * @date 23, Jan 2020 (13:09)
 */
class WeatherViewModelTest {
  @get:Rule
  var rule: MockitoRule = MockitoJUnit.rule()

  @get:Rule
  var ruleRxImmediate = RxImmediateSchedulerRule()
  private val weatherViewModel = WeatherViewModel()

  @get:Rule
  var liveDataRule: TestRule = InstantTaskExecutorRule()

  companion object {
    const val MUMBAI = "mumbai"
    const val LONDON = "london"
    const val BANGALORE = "bangalore"
    private const val TIME_1 = "00:00:00"
    private const val TIME_2 = "03:00:00"
    private const val TIME_3 = "06:00:00"
    private const val TIME_4 = "09:00:00"
    private const val TIME_5 = "12:00:00"
    private const val TIME_6 = "15:00:00"
    private const val TIME_7 = "18:00:00"
    private const val TIME_8 = "21:00:00"
    const val DATE_1 = "2020-01-23"
    const val DATE_2 = "2020-01-24"
    const val DATE_3 = "2020-01-25"
    const val DATE_4 = "2020-01-26"
    const val DATE_5 = "2020-01-27"
    private val TIMES = arrayOf(TIME_1, TIME_2, TIME_3, TIME_4, TIME_5, TIME_6, TIME_7, TIME_8)
    private val DATES = arrayOf(DATE_1, DATE_2, DATE_3, DATE_4, DATE_5)
    val FORECAST_WEATHER_LIST = arrayListOf<CityWeatherDetail>()

    init {
      for (date in DATES) {
        for (time in TIMES) {
          FORECAST_WEATHER_LIST.add(CityWeatherDetail(dtTxt = date + " " + time))
        }
      }
    }

    val MUMBAI_WEATHER = CityWeatherDetail(name = MUMBAI)
    val LONDON_WEATHER = CityWeatherDetail(name = LONDON)
    val BANGALORE_WEATHER = CityWeatherDetail(name = BANGALORE)
  }

  @Before
  fun setUp() {
    ServiceLocator.apiService(Mockito.mock(WeatherService::class.java))
    Mockito.`when`(ServiceLocator.weatherService()?.weatherDetailsOf(MUMBAI))
      .thenReturn(
        Single.just(MUMBAI_WEATHER)
      )
    Mockito.`when`(ServiceLocator.weatherService()?.weatherDetailsOf(BANGALORE))
      .thenReturn(
        Single.just(BANGALORE_WEATHER)
      )
    Mockito.`when`(ServiceLocator.weatherService()?.weatherDetailsOf(LONDON))
      .thenReturn(
        Single.just(LONDON_WEATHER)
      )

  }

  @Test
  fun weather_request_for_3_to_7_cities_should_return_weather_of_given_cities() {
    weatherViewModel.weather("mumbai,london,bangalore")
    assertTrue(weatherViewModel.weatherList().value?.data!![0] == MUMBAI_WEATHER)
    assertTrue(weatherViewModel.weatherList().value?.data!![1] == LONDON_WEATHER)
    assertTrue(weatherViewModel.weatherList().value?.data!![2] == BANGALORE_WEATHER)
    Mockito.`when`(
      ServiceLocator.weatherService()!!.weatherDetailsOf(
        ArgumentMatchers.anyString(),
        ArgumentMatchers.anyString()
      )
    )
      .thenReturn(
        Single.just(LONDON_WEATHER)
      )
    weatherViewModel.weather("mumbai,london,bangalore,newyork")
    assertTrue(weatherViewModel.weatherList().value!!.data!!.size == 4)

    weatherViewModel.weather("mumbai,london,bangalore,newyork,paris")
    assertTrue(weatherViewModel.weatherList().value!!.data!!.size == 5)

    weatherViewModel.weather("mumbai,london,bangalore,newyork,paris,pune")
    assertTrue(weatherViewModel.weatherList().value!!.data!!.size == 6)

    weatherViewModel.weather("mumbai,london,bangalore,newyork,paris,pune,delhi")
    assertTrue(weatherViewModel.weatherList().value!!.data!!.size == 7)
  }

  @Test
  fun weather_request_for_2_cities_must_return_error() {
    weatherViewModel.weather("mumbai,london")
    assertEquals(ERROR, weatherViewModel.weatherList().value!!.status)
  }

  @Test
  fun weather_request_for_more_than_7_cities_must_return_error() {
    weatherViewModel.weather("mumbai,london,bangalore,newyork,paris,pune,delhi,jodhpur")
    assertEquals(ERROR, weatherViewModel.weatherList().value!!.status)
  }

  @Test
  fun forecast() {
    Mockito.`when`(ServiceLocator.weatherService()!!.forecastOf(MUMBAI))
      .thenReturn(
        Single.just(Forecast(list = FORECAST_WEATHER_LIST))
      )
    weatherViewModel.forecast(MUMBAI)
    //check if we have hashmap of 5 days
    assertEquals(5, weatherViewModel.forecast().value!!.data!!.keys.size)
    //check hash map keys have the proper date value
    assertEquals(DATE_1, weatherViewModel.forecast().value?.data!!.keys.toList()[0])
    assertEquals(DATE_2, weatherViewModel.forecast().value?.data!!.keys.toList()[1])
    assertEquals(DATE_3, weatherViewModel.forecast().value?.data!!.keys.toList()[2])
    assertEquals(DATE_4, weatherViewModel.forecast().value?.data!!.keys.toList()[3])
    assertEquals(DATE_5, weatherViewModel.forecast().value?.data!!.keys.toList()[4])
  }
}