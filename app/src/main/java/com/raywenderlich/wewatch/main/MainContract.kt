package com.raywenderlich.wewatch.main

import com.raywenderlich.wewatch.model.Movie

class MainContract {

    interface PresenterInterface {
      fun getMyMoviesList()
      fun stop()
      fun  onDeleteTapped(movies: HashSet<Movie>)
    }

    interface  ViewInterface{

        fun displayNoMovies()
        fun displayMovie(movieList: List<Movie>)
        fun displayError(error : String)
        fun displayMessage(message: String)

    }
}