package com.raywenderlich.wewatch.main

import android.util.Log
import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val viewInterface : MainContract.ViewInterface,
                             private val dataSource: LocalDataSource) : MainContract.PresenterInterface {

    private val compositeDisposable = CompositeDisposable()


    private val myMoviesObservable: Observable<List<Movie>>
        get() = dataSource.allMovies


    private val observer: DisposableObserver<List<Movie>>
        get() = object : DisposableObserver<List<Movie>>() {

            override fun onNext(movieList: List<Movie>) {
              if(movieList.isEmpty()){
                  viewInterface.displayNoMovies()
              }else {
                  viewInterface.displayMovie(movieList)
              }
            }

            override fun onError(@NonNull e: Throwable) {
                Log.e(TAG, "Error fetching movie list", e)
                viewInterface.displayError("Error fetching movie list")
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    private  val TAG = "MainPresenter"
    override fun getMyMoviesList() {
            val myMoviesDisposable = myMoviesObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(observer)

    compositeDisposable.add(myMoviesDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }




    override fun onDeleteTapped(movies: HashSet<Movie>) {
      for(movie in movies){
          dataSource.delete(movie as Movie)
      }

        if(movies.size == 1){
            viewInterface.displayMessage("Movie Deleted")
        }else if(movies.size > 1){
            viewInterface.displayMessage("Movies Deleted")
        }
    }

}