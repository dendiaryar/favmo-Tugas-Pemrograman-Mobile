package com.example.favmo.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Movie(@SerializedName("original_title") val originalTitle : String?,
                 @SerializedName("overview") val overview : String?,
                 @SerializedName("poster_path") val posterPath : String?,
                 @SerializedName("popularity") val popularity : Double?,
                 @SerializedName("vote_average") val voteaverage : Double?)

data class MovieResponse(var page : Int,
                         val results : ArrayList<Movie>,
                         val totalResult : Int,
                         val totalPage : Int)