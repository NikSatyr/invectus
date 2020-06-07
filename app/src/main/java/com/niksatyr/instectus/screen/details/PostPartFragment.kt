package com.niksatyr.instectus.screen.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.api.load
import com.niksatyr.instectus.R
import kotlinx.android.synthetic.main.fragment_post_part.*

class PostPartFragment : Fragment(R.layout.fragment_post_part) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaUrl = arguments?.getString(EXTRA_MEDIA_URL)
            ?: throw IllegalArgumentException("This fragment requires a media url to be passed")

        ivPostPart.load(mediaUrl)
    }

    companion object {

        private const val EXTRA_MEDIA_URL = "EXTRA_MEDIA_URL"

        fun newInstance(mediaUrl: String): PostPartFragment {
            val args = Bundle().also {
                it.putString(EXTRA_MEDIA_URL, mediaUrl)
            }
            return PostPartFragment().also {
                it.arguments = args
            }
        }

    }

}