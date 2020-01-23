package com.kailashdabhi.weather.base

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * @author Kailash Dabhi
 * @email kailash09dabhi@gmail.com
 * @date 04/10/2018
 */
object RxUtils {
  fun <O> singleObserver(): DisposableSingleObserver<O> {
    return object : DisposableSingleObserver<O>() {
      override fun onSuccess(o: O) {}
      override fun onError(e: Throwable) {}
    }
  }

  fun <O> observer(): DisposableObserver<O> {
    return object : DisposableObserver<O>() {
      override fun onNext(o: O) {}
      override fun onError(e: Throwable) {}
      override fun onComplete() {}
    }
  }

  fun <T> applyNetworkSchedulers(): SingleTransformer<T, T> {
    return SingleTransformer { upstream: Single<T> ->
      upstream.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
    }
  }

  fun <T> applySchedulers(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream: Observable<T> ->
      upstream.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
    }
  }
}