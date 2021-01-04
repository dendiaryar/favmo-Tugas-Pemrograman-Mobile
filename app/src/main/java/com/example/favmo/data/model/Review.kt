package com.example.favmo.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Review (

    @SerializedName("author") val author : String,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("id") val id : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("url") val url : String
): Parcelable

data class ReviewResponse (
    val id: Int,
    var page : Int,
    val results : ArrayList<Review>,
    val totalResult : Int,
    val totalPage : Int)
