package com.example.wasan.RecyclerView

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wasan.Model.TvShow
import com.example.wasan.R
import com.example.wasan.ShowsViewModel
import kotlinx.android.synthetic.main.local_item_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LocalAdapter(private val viewModel: ShowsViewModel): RecyclerView.Adapter<LocalAdapter.ItemViewHolder>() {
    private var tvShows: List<TvShow> = emptyList()

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.local_item_row,
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
            if (show.premiered == "null") tvDate.visibility = View.GONE
            if (show.ended != "null") tvDate.text = "${show.premiered} - ${show.ended}"
            else tvDate.text = show.premiered

            if (show.image.isNotEmpty()) {
                try {
                    Glide.with(holder.itemView.context).load(show.image).into(imgShow)
                } catch (e: Exception) {
                    imgShow.setImageDrawable(resources.getDrawable(R.drawable.noimage))
                }
            }

            btnDelete.setOnClickListener {
                //alert

                val builder = AlertDialog.Builder(holder.itemView.context)
                //set title for alert dialog
                builder.setTitle("Delete show")
                //set message for alert dialog
                builder.setMessage("Are you sure you want to delete ${show.name}" )
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.deleteTVShow(show)
                    }                }
                //performing cancel action
                builder.setNeutralButton("Cancel"){dialogInterface , which ->
                }
                //performing negative action
                builder.setNegativeButton("No"){dialogInterface, which ->
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()


            }
            cvShow.setOnClickListener{
                if (show.summary.isNotEmpty()){
                    Toast.makeText(holder.itemView.context, show.summary,Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(holder.itemView.context, "No summary",Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    fun update(newList: List<TvShow>) {
        this.tvShows = newList
        notifyDataSetChanged()
    }
}
