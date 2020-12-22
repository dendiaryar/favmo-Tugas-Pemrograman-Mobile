package com.example.favmo.data.helper

import android.database.Cursor
import com.example.favmo.data.db.DatabaseContract
import com.example.favmo.data.model.Movie

object MappingHelper {

    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<Movie> {
        val notesList = ArrayList<Movie>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE))
                val overview = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW))
                val posterpath = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTERPATH))
                val popularity = getDouble(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POPULARITY))
                val voteaverage = getDouble(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.VOTEAVERAGE))
                notesList.add(Movie(id, title, overview, posterpath,popularity,voteaverage ))
            }
        }
        return notesList
    }
}