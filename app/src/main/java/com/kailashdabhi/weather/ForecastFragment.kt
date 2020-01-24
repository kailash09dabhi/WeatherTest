package com.kailashdabhi.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kailashdabhi.weather.R.layout
import com.kailashdabhi.weather.base.RxUtils
import com.kailashdabhi.weather.base.Status.ERROR
import com.kailashdabhi.weather.base.Status.LOADING
import com.kailashdabhi.weather.base.Status.SUCCESS
import com.kailashdabhi.weather.data.LocationRepository
import kotlinx.android.synthetic.main.fragment_forecast.progress
import kotlinx.android.synthetic.main.fragment_forecast.recyclerView
import kotlinx.android.synthetic.main.fragment_forecast.spinner

/**
 * @author kailash09dabhi@gmail.com
 * @date 22, Jan 2020 (11:18)
 */
class ForecastFragment : Fragment() {
  companion object {
    fun newInstance() = ForecastFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layout.fragment_forecast, container, false)
  }

  private val viewModel by viewModels<WeatherViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recyclerView.layoutManager = LinearLayoutManager(context)
    context?.let {
      LocationRepository().getLastLocation(it)
        .doOnSuccess { location ->
          activity?.title = location + " Forecast"
          viewModel.forecast(location)
        }
        .doOnError {
          Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
        .subscribe(RxUtils.singleObserver())
    }
    viewModel.forecast().observe(viewLifecycleOwner, Observer { resource ->
      when (resource.status) {
        SUCCESS -> {
          progress.visibility = View.GONE
          spinner.visibility = View.VISIBLE
          context?.let {
            spinner.adapter = ArrayAdapter<String>(
              it,
              android.R.layout.simple_spinner_dropdown_item,
              resource.data?.keys?.toList() ?: listOf()
            )
            spinner.onItemSelectedListener = object : OnItemSelectedListener {
              override fun onNothingSelected(parent: AdapterView<*>?) {
              }

              override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
              ) {
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter =
                  WeatherAdapter(resource.data?.get((view as TextView).text) ?: listOf())
              }
            }
          }

        }
        ERROR -> {
          Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
        }
        LOADING -> {
          progress.visibility = View.VISIBLE
          recyclerView.visibility = View.GONE
          spinner.visibility = View.GONE
        }
      }
    })
  }
}