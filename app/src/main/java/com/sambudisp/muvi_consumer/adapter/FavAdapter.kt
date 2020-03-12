package com.sambudisp.muvi_consumer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sambudisp.muvi.model.localstorage.FavModel
import com.sambudisp.muvi_consumer.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_fav.view.*

class FavAdapter() :
    RecyclerView.Adapter<FavAdapter.NoteViewHolder>() {

    var listFav = ArrayList<FavModel>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listFav.clear()
            }
            this.listFav.addAll(listNotes)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fav, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listFav[position])
    }

    override fun getItemCount(): Int = this.listFav.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: FavModel) {
            with(itemView) {
                val savedAt = "Saved at : " + fav.date
                tv_saved_id.text = fav.savedId
                tv_date.text = savedAt
                tv_saved_title.text = fav.title
                tv_saved_desc.text = fav.desc

                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w342" + fav.poster)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(img_saved_poster)
            }
        }
    }
}