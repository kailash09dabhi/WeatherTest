package com.kailashdabhi.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kailashdabhi.weather.R.id
import com.kailashdabhi.weather.R.layout

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(
          id.fragmentContainer,
          WeatherFragment.newInstance()
        )
        .commit()
    }
  }
}
