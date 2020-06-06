package com.niksatyr.instectus.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.niksatyr.instectus.R
import com.niksatyr.instectus.model.Post

class PostsAdapter(
    private val posts: MutableList<Post> = ArrayList()
) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    fun setData(posts: List<Post>) {
        this.posts.apply {
            clear()
            addAll(posts)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        )
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        val thumbnailUrl = post.getPreviewUrl()

        holder.apply {
            imagePostPreview.load(thumbnailUrl)

            if (post.isVideo()) {
                imagePostTypeIndicator.setImageResource(R.drawable.ic_play_arrow)
            } else if (post.isCarousel()) {
                imagePostTypeIndicator.setImageResource(R.drawable.ic_carousel)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePostPreview: ImageView = itemView.findViewById(R.id.ivPostPreview)
        val imagePostTypeIndicator: ImageView = itemView.findViewById(R.id.ivTypeIndicator)
    }

}