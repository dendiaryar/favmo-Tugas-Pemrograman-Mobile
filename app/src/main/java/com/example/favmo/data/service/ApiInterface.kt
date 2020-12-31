package com.example.favmo.data.service

import com.example.favmo.data.model.Movie
import com.example.favmo.data.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/latest")
    fun getMovieLatest(@Query("api_key") apiKey : String) : Call<Movie>
    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key") apiKey: String, @Query("page") page : Int) : Call<MovieResponse>
    @GET("movie/top_rated")
    fun getMovieTopRated(@Query("api_key") apiKey : String) : Call<MovieResponse>
}