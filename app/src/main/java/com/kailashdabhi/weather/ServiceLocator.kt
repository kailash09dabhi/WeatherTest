package com.kailashdabhi.weather

import com.mobimove.nursery.api.WeatherService

/**
 * @author Kailash Dabhi
 * @email kailash09dabhi@gmail.com
 * @date 18/02/2019
 */
object ServiceLocator {
  private var weatherService: WeatherService? = null
  fun weatherService(): WeatherService? {
    return weatherService
  }

  fun apiService(weatherService: WeatherService) {
    ServiceLocator.weatherService = weatherService
  }
}