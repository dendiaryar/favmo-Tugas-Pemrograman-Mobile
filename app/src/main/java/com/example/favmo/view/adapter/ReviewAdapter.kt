package com.example.favmo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.favmo.R
import com.example.favmo.data.model.Movie
import com.example.favmo.data.model.Review
import kotlinx.android.synthetic.main.review.view.*
import kotlinx.android.synthetic.main.row.view.*


class ReviewAdapter(private val reviews: ArrayList<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {
    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.bind(reviews[position])
    }



    override fun getItemCount() = reviews.size

    class ReviewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        private var reviews : Review ?= null
        private var view : View = view

        fun bind(reviews: Review) {
            this.reviews = reviews
            view.tvQuote.setText(reviews.content)
            view.reviewAuthor.setText(reviews.author)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        return ReviewHolder(LayoutInflater.from(parent.context).inflate(R.layout.review, parent, false))
    }

}