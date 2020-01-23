package com.kailashdabhi.weather.base

import com.kailashdabhi.weather.base.Status.ERROR
import com.kailashdabhi.weather.base.Status.LOADING
import com.kailashdabhi.weather.base.Status.SUCCESS

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
  companion object {
    fun <T> success(data: T?): Resource<T> {
      return Resource(SUCCESS, data, null)
    }

    fun <T> error(msg: String, data: T?): Resource<T> {
      return Resource(ERROR, data, msg)
    }

    fun <T> loading(data: T?): Resource<T> {
      return Resource(LOADING, data, null)
    }
  }
}