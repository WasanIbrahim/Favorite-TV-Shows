package com.example.wasan.RecyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wasan.Database.TvShowDB
import com.example.wasan.Model.TvShow
import com.example.wasan.R
import kotlinx.android.synthetic.main.api_item_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class APIAdapter (private val context: Context?): RecyclerView.Adapter<APIAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var tvShows: MutableList<TvShow> = mutableListOf()

    private val database by lazy { TvShowDB.getInstance(context!!).tvShowDao() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.api_item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val show = tvShows[position]

        holder.itemView.apply {

            tvTitle.text = show.name
            tvLanguage.text = show.language
            tvRating.text = show.rating

            //if show not rated
            if(show.rating == "null") tvRating.visibility = View.GONE

            //if show has no date
            if(show.premiered == "null") tvDate.visibility = View.GONE

            if(show.ended != "null" ) tvDate.text = "${show.premiered} - ${show.ended}"

            else tvDate.text = show.premiered

            if(show.image.isNotEmpty()){
                try {
                    Glide.with(holder.itemView.context).load(show.image).into(imgShow)
                }catch (e: Exception){
                    imgShow.setImageDrawable(resources.getDrawable(R.drawable.noimage))
                }
            }
            btnAdd.setOnClickListener {
                CoroutineScope(IO).launch {
                    database.insertShow(show)
                    Log.d("API ADAPTER", "here is the show: $show")
                }
                Toast.makeText(holder.itemView.context, "${show.name} has been successfully added", Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }
    fun update(newList: MutableList<TvShow>){
        this.tvShows = newList
        notifyDataSetChanged()
    }
}