package com.example.favmo.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.favmo.data.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME


internal class DatabaseHelper(context : Context)  : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavoriteColumns.TITLE} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.OVERVIEW} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.POSTERPATH} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.POPULARITY} REAL NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.VOTEAVERAGE} REAL NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}