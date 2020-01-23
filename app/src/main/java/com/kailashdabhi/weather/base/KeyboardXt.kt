package com.kailashdabhi.weather.base

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Kailash Dabhi
 * @email kailash09dabhi@gmail.com
 * @date 06-01-2020
 */
fun View.hideKeyboard(then: () -> Unit = {}) {
  val imm: InputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(windowToken, 0)
}