package com.example.compose

import com.example.compose.data.RetrofitApi
import com.example.compose.model.CharacterModel
import com.example.compose.model.Episode

class MovieRepository {
    suspend fun getPopularMovies(page: Int) : List<CharacterModel> {
        val response = RetrofitApi.retrofit.loadList(page = page)
        return response.body()?.results ?: emptyList()
    }

    suspend fun getEpisodeById(id: Int) : Episode {
        val response = RetrofitApi.retrofit.getEpisode(id)
        return response.body() ?: Episode(0, "", "")
    }
}