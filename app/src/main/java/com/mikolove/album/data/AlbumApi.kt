package com.mikolove.album.data

import com.mikolove.album.data.entities.AlbumData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val API_URL = "https://static.leboncoin.fr/img/shared/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(API_URL)
    .build()

interface AlbumApiService {
    @GET("technical-test.json")
    fun getAlbumData(): Call<List<AlbumData>>
}

object AlbumApi {
    val retrofitService : AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java) }
}