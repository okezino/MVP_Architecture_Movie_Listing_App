package com.raywenderlich.wewatch.add

import com.raywenderlich.wewatch.model.LocalDataSource
import com.raywenderlich.wewatch.model.Movie

class AddMoviePresenter(val viewInterface: AddMovieContract.ViewInterface,
                                 val dataSource: LocalDataSource) : AddMovieContract.PresenterInterface {
    override fun addMovie(title: String, releaseDate: String, posterPath: String) {

              val movie = Movie(title, releaseDate, posterPath)
              dataSource.insert(movie)
              viewInterface.returnToMain()

    }


}