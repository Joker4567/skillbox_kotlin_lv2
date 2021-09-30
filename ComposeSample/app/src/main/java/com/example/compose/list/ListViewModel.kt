package com.example.compose.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.compose.MovieRepository
import com.example.compose.MovieSource
import com.example.compose.model.CharacterModel
import kotlinx.coroutines.flow.Flow

class ListViewModel(
        movieRepository: MovieRepository
) : ViewModel() {

    val movies: Flow<PagingData<CharacterModel>> = Pager(PagingConfig(pageSize = 20)) {
        MovieSource(movieRepository)
    }.flow
}