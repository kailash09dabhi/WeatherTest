package com.kailashdabhi.weather

import android.Manifest.permission
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kailashdabhi.weather.R.layout
import com.kailashdabhi.weather.base.Status.ERROR
import com.kailashdabhi.weather.base.Status.LOADING
import com.kailashdabhi.weather.base.Status.SUCCESS
import com.kailashdabhi.weather.base.hideKeyboard
import kotlinx.android.synthetic.main.fragment_weather.input
import kotlinx.android.synthetic.main.fragment_weather.progress
import kotlinx.android.synthetic.main.fragment_weather.recyclerView
import kotlinx.android.synthetic.main.fragment_weather.searchIcon

/**
 * @author kailash09dabhi@gmail.com
 * @date 22, Jan 2020 (11:18)
 */
class WeatherFragment : Fragment() {
  companion object {
    fun newInstance() = WeatherFragment()
  }

  object RequestCodes {
    const val LOCATION_PERMISSION = 16
  }

  private val viewModel by viewModels<WeatherViewModel>()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layout.fragment_weather, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setHasOptionsMenu(true)
    searchIcon.setOnClickListener {
      input.hideKeyboard()
      viewModel.weather(input.text.toString())
    }
    viewModel.weatherList().observe(viewLifecycleOwner, Observer {
      when (it.status) {
        SUCCESS -> {
          progress.visibility = View.GONE
          recyclerView.visibility = View.VISIBLE
          recyclerView.layoutManager = LinearLayoutManager(context)
          recyclerView.adapter = WeatherAdapter(it.data!!)
        }
        ERROR -> {
          Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
        LOADING -> {
          progress.visibility = View.VISIBLE
          recyclerView.visibility = View.GONE
        }
      }
    })
  }

  override fun onCreateOptionsMenu(
    menu: Menu,
    inflater: MenuInflater
  ) {
    super.onCreateOptionsMenu(menu, inflater)
    inflater.inflate(R.menu.weather, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    if (id == R.id.action_forecast) {
      if (PermissionChecker.checkSelfPermission(activity!!, permission.ACCESS_COARSE_LOCATION)
        == PermissionChecker.PERMISSION_DENIED
      ) {
        requestPermissions(
          arrayOf(permission.ACCESS_COARSE_LOCATION),
          RequestCodes.LOCATION_PERMISSION
        )
      } else {
        navigateToForecast()
      }
    }
    return true
  }

  private fun navigateToForecast() {
    parentFragmentManager.beginTransaction()
      .replace(R.id.fragmentContainer, ForecastFragment.newInstance())
      .addToBackStack(null)
      .commit()
  }

  override fun onRequestPermissionsResult(
    requestCode: Int, permissions: Array<String?>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      RequestCodes.LOCATION_PERMISSION -> {
        if (grantResults.size == 1 && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
          navigateToForecast()
        } else {
          if (!ActivityCompat.shouldShowRequestPermissionRationale(
              activity!!,
              permission.ACCESS_COARSE_LOCATION
            )
          ) {
            Toast.makeText(
              context,
              "Open settings and enable location permission",
              Toast.LENGTH_SHORT
            ).show()
          } else {
            Toast.makeText(
              activity!!,
              "Location access required to see forecast for a current location!",
              Toast.LENGTH_LONG
            ).show()
          }
        }
      }
    }
  }
}