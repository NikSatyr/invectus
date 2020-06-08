package com.niksatyr.invectus

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.niksatyr.invectus.dto.UserMediaResponse
import com.niksatyr.invectus.model.Post
import com.niksatyr.invectus.model.UserInfo
import com.niksatyr.invectus.network.UserInfoService
import com.niksatyr.invectus.network.UserMediaService
import com.niksatyr.invectus.repo.InstagramRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.util.*

class InstagramRepositoryTest {

    private val mockUserInfo = UserInfo("testus", "12345678")

    private val mockPosts = listOf(
        Post("1", "testus", Post.TYPE_CAROUSEL, "i.com", "Caption", Date(), null),
        Post("2", "testus", Post.TYPE_VIDEO, "i.com", "Caption", Date(), "i.com"),
        Post("3", "testus", Post.TYPE_IMAGE, "i.com", "Caption", Date(), null)
    )

    private val mockUserMediaService: UserMediaService = mock {
        on { getUserMedia() } doReturn Single.just(UserMediaResponse(mockPosts))
        on { getCarouselChildren(any()) } doReturn Single.just(UserMediaResponse(mockPosts))
    }

    private val mockUserInfoService: UserInfoService = mock {
        on { getUserInfo() } doReturn Single.just(mockUserInfo)
    }

    private val instagramRepository = InstagramRepository(mockUserMediaService, mockUserInfoService)

    @Test
    fun test_getUserInfo() {
        val testObserver = instagramRepository.getUserInfo().test()
        testObserver.assertValue(mockUserInfo)
    }

    @Test
    fun test_getUserMedia() {
        val testObserver = instagramRepository.getUserMedia().test()
        testObserver.assertValue(UserMediaResponse(mockPosts))
    }

    @Test
    fun test_getCarouselChildren() {
        val post = mockPosts.first { it.type == Post.TYPE_CAROUSEL }
        val testObserver = instagramRepository.getCarouselChildren(post).test()
        testObserver.assertValue(UserMediaResponse(mockPosts))
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_getCarouselChildren_invalidType() {
        val post = mockPosts.first { it.type == Post.TYPE_IMAGE }
        val testObserver = instagramRepository.getCarouselChildren(post).test()
        testObserver.assertValue(UserMediaResponse(mockPosts))
    }

}