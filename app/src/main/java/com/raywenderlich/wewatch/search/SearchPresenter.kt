package com.raywenderlich.wewatch.search

import android.util.Log
import androidx.room.RoomDatabase
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.RemoteDataSource
import com.raywenderlich.wewatch.model.TmdbResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenter(val viewInterface: SearchContract.ViewInterface, val dataSource: RemoteDataSource) : SearchContract.PresenterInterface {

    private val TAG = "Search Presenter"
    private val compositeDisposable = CompositeDisposable()
    override fun getSearchResults(query: String) {
        val searchResultsDisposable = searchResultsObservable(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)

        compositeDisposable.add(searchResultsDisposable)
    }
    val searchResultsObservable: (String) -> Observable<TmdbResponse> = { query -> dataSource.searchResultsObservable(query) }

    val observer: DisposableObserver<TmdbResponse>
        get() = object : DisposableObserver<TmdbResponse>() {

            override fun onNext(@NonNull tmdbResponse: TmdbResponse) {
        Log.d(TAG, "OnNext" + tmdbResponse.totalResults)
                viewInterface.displayResult(tmdbResponse)
            }

            override fun onError(@NonNull e: Throwable) {
            Log.e(TAG, "Error fetching movie data.", e)
                viewInterface.displayError("Error fetching movie data.")
            }

            override fun onComplete() {
             Log.d(TAG, "Completed")
            }
        }

    override fun stop() {
        compositeDisposable.clear()
    }
}