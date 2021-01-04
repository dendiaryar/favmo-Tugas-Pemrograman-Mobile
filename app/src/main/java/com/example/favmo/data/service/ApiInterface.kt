package com.example.favmo.data.service

import com.example.favmo.data.model.MovieResponse
import com.example.favmo.data.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key") apiKey: String, @Query("page") page : Int) : Call<MovieResponse>
    @GET("movie/top_rated")
    fun getMovieTopRated(@Query("api_key") apiKey : String,@Query("page") page : Int) : Call<MovieResponse>
    @GET("movie/{id}/reviews")
    fun getReview (@Path("id") path:String, @Query("api_key") apiKey : String) : Call<ReviewResponse>
}