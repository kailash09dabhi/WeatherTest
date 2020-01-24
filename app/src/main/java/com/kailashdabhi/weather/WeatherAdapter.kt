package com.kailashdabhi.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kailashdabhi.weather.R.layout
import com.kailashdabhi.weather.WeatherAdapter.ViewHolder
import com.kailashdabhi.weather.data.model.CityWeatherDetail
import kotlinx.android.synthetic.main.item_weather.view.city
import kotlinx.android.synthetic.main.item_weather.view.description
import kotlinx.android.synthetic.main.item_weather.view.temperature
import kotlinx.android.synthetic.main.item_weather.view.windSpeed

class WeatherAdapter(private val items: List<CityWeatherDetail>) :
  RecyclerView.Adapter<ViewHolder>() {

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(layout.item_weather, parent, false)
    )
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    val cityWeatherDetail = items[position]
    if (cityWeatherDetail.name.isNullOrEmpty()) holder.city.visibility = View.GONE else {
      holder.city.visibility = View.VISIBLE
      holder.city.text = "City - " + cityWeatherDetail.name
    }
    holder.windSpeed.text = "Wind speed - " + cityWeatherDetail.wind?.speed.toString()
    holder.description.text = "Description - " + cityWeatherDetail.weather?.get(0)?.description
    holder.temperature.text =
      "temp min - " + cityWeatherDetail.main?.tempMin + ", temp max - " + cityWeatherDetail.main?.tempMax
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val description = view.description
    val windSpeed = view.windSpeed
    val temperature = view.temperature
    val city = view.city
  }
}
