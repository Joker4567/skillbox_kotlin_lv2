package com.example.compose.data

import com.example.compose.model.Episode
import com.example.compose.model.Response
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitApi {
    @GET("/api/character")
    suspend fun loadList(@Query("page") page: Int): retrofit2.Response<Response>

    @GET("api/episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int) : retrofit2.Response<Episode>

    companion object {
        const val pageSize = 20

        val retrofit by lazy {
            Retrofit
                    .Builder()
                    .baseUrl("https://rickandmortyapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create<RetrofitApi>()
        }
    }
}