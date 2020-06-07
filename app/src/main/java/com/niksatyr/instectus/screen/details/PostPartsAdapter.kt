package com.niksatyr.instectus.screen.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.niksatyr.instectus.R

class PostPartsAdapter(
    private val mediaUrls: MutableList<String> = ArrayList()
) : RecyclerView.Adapter<PostPartsAdapter.ViewHolder>() {

    fun setData(mediaUrls: List<String>) {
        this.mediaUrls.apply {
            clear()
            addAll(mediaUrls)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_post_part, parent, false)
        )
    }

    override fun getItemCount() = mediaUrls.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imagePostPart.load(mediaUrls[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePostPart: ImageView = itemView.findViewById(R.id.ivPostPart)
    }

}