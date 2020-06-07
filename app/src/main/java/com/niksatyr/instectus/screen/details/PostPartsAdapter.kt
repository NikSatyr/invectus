package com.niksatyr.instectus.screen.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.niksatyr.instectus.R
import com.niksatyr.instectus.model.Post

class PostPartsAdapter(
    private val mediaUrls: MutableList<Pair<String, String>> = ArrayList()
) : RecyclerView.Adapter<PostPartsAdapter.ViewHolder>() {

    fun setData(mediaUrls: List<Pair<String, String>>) {
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
        val postType = mediaUrls[position].second
        val mediaUrl = mediaUrls[position].first

        if (postType == Post.TYPE_IMAGE) loadImage(holder, mediaUrl) else loadVideo(holder, mediaUrl)
    }

    private fun loadImage(holder: ViewHolder, url: String) {
        holder.apply {
            videoViewPostPart.visibility = View.GONE
            imageViewPostPart.visibility = View.VISIBLE

            imageViewPostPart.load(url) {
                placeholder(R.drawable.ic_cloud)
            }
        }
    }

    private fun loadVideo(holder: ViewHolder, url: String) {
        holder.apply {
            videoViewPostPart.visibility = View.VISIBLE
            imageViewPostPart.visibility = View.GONE

            videoViewPostPart.setVideoPath(url)
            videoViewPostPart.start()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPostPart: ImageView = itemView.findViewById(R.id.ivPostPartImage)
        val videoViewPostPart: VideoView = itemView.findViewById(R.id.ivPostPartVideo)
    }

}