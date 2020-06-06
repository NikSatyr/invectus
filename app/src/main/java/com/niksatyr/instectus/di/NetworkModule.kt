package com.niksatyr.instectus.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.niksatyr.instectus.Constants
import com.niksatyr.instectus.network.UserMediaService
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val HEADER_ACCESS_TOKEN = "access_token"

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }
    single { provideWebService<UserMediaService>(get()) }
}

private fun provideOkHttpClient() = OkHttpClient.Builder()
    .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .addInterceptor {
        val request = it.request()
        val newUrl = request.url.newBuilder().addQueryParameter(HEADER_ACCESS_TOKEN, Constants.ACCESS_TOKEN).build()
        it.proceed(request.newBuilder().url(newUrl).build())
    }
    .build()

private fun provideGson() = GsonBuilder()
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    .create()

private fun provideRetrofit(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .build()

private inline fun <reified T> provideWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}