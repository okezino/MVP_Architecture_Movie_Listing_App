package com.raywenderlich.wewatch.search

import com.raywenderlich.wewatch.model.TmdbResponse


class SearchContract {

    interface  ViewInterface{

        fun displayResult(tmdbResponse: TmdbResponse)
        fun displayMessage(message : String)
        fun displayError(message : String)

    }

    interface   PresenterInterface{
        fun getSearchResults(query: String)
        fun stop()

    }
}