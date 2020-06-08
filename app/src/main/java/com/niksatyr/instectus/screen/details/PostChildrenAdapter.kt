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

class PostChildrenAdapter(
    private val mediaUrlsWithTypes: MutableList<Pair<String, String>> = ArrayList()
) : RecyclerView.Adapter<PostChildrenAdapter.ViewHolder>() {

    fun setData(mediaUrlsWithTypes: List<Pair<String, String>>) {
        this.mediaUrlsWithTypes.apply {
            clear()
            addAll(mediaUrlsWithTypes)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post_child, parent, false)
        )
    }

    override fun getItemCount() = mediaUrlsWithTypes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postType = mediaUrlsWithTypes[position].second
        val mediaUrl = mediaUrlsWithTypes[position].first

        if (postType == Post.TYPE_IMAGE) loadImage(holder, mediaUrl) else loadVideo(holder, mediaUrl)
    }

    private fun loadImage(holder: ViewHolder, url: String) {
        holder.videoViewPostChildVideo.visibility = View.GONE

        holder.imageViewPostChildImage.apply {
            visibility = View.VISIBLE
            load(url) {
                placeholder(R.drawable.ic_cloud)
            }
        }
    }

    private fun loadVideo(holder: ViewHolder, url: String) {
        holder.imageViewPostChildImage.visibility = View.GONE

        holder.videoViewPostChildVideo.apply {
            visibility = View.VISIBLE
            setOnPreparedListener {
                it.isLooping = true
            }
            setVideoPath(url)
            start()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPostChildImage: ImageView = itemView.findViewById(R.id.ivPostChildImage)
        val videoViewPostChildVideo: VideoView = itemView.findViewById(R.id.ivPostChildVideo)
    }

}