package com.kailashdabhi.weather.data

import com.kailashdabhi.weather.ServiceLocator
import com.kailashdabhi.weather.data.model.CityWeatherDetail
import com.mobimove.nursery.api.WeatherService
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

/**
 * @author kailash09dabhi@gmail.com
 * @date 23, Jan 2020 (11:21)
 */
class WeatherRepositoryTest {
  @get:Rule
  var rule = MockitoJUnit.rule()

  @Before
  fun setUp() {
    ServiceLocator.apiService(Mockito.mock(WeatherService::class.java))
  }

  companion object {
    const val MUMBAI = "mumbai"
    const val LONDON = "london"
    const val BANGALORE = "bangalore"
    val MUMBAI_WEATHER = CityWeatherDetail(name = MUMBAI)
    val LONDON_WEATHER = CityWeatherDetail(name = LONDON)
    val BANGALORE_WEATHER = CityWeatherDetail(name = BANGALORE)
  }

  val cityList = listOf(MUMBAI, LONDON, BANGALORE)

  @Test
  fun weatherOnce_should_fetch_weather_of_supplied_cities_and_return_list_of_city_weather() {
    `when`(ServiceLocator.weatherService()!!.weatherDetailsOf(MUMBAI)).thenReturn(
      Single.just(MUMBAI_WEATHER)
    )
    `when`(ServiceLocator.weatherService()!!.weatherDetailsOf(BANGALORE)).thenReturn(
      Single.just(BANGALORE_WEATHER)
    )
    `when`(ServiceLocator.weatherService()!!.weatherDetailsOf(LONDON)).thenReturn(
      Single.just(LONDON_WEATHER)
    )
    val test = WeatherRepository.instance.weatherOnce(cityList).test()
    test.awaitTerminalEvent()
    test.assertComplete()
    test.assertNoErrors()
    assert(test.values()[0][0] == MUMBAI_WEATHER)
    assert(test.values()[0][1] == LONDON_WEATHER)
    assert(test.values()[0][2] == BANGALORE_WEATHER)
  }

}