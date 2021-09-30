package com.example.compose

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.compose.model.CharacterModel

class MovieSource(
        private val movieRepository: MovieRepository
) : PagingSource<Int, CharacterModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = movieRepository.getPopularMovies(nextPage)

            LoadResult.Page(
                    data = movieListResponse,
                    prevKey = params.key?.let { it - 1 },
                    nextKey = (params.key ?: 0) + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? = null
}