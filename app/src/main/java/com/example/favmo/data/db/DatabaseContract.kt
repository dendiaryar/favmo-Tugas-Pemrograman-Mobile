package com.example.favmo.data.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favoritemovie"
            const val _ID = "id"
            const val TITLE = "originalTitle"
            const val OVERVIEW = "overview"
            const val POSTERPATH  = "posterPath"
            const val POPULARITY = "popularity"
            const val VOTEAVERAGE = "voteaverage"
        }
    }
}