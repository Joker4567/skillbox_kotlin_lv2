package com.example.compose.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.MovieRepository
import com.example.compose.model.CharacterModel
import com.example.compose.model.Episode
import kotlinx.coroutines.launch

class DetailViewModel(
        private val movieRepository: MovieRepository
) : ViewModel() {

    private val _itemList = MutableLiveData<List<Episode>>()
    val itemList: LiveData<List<Episode>>
        get() = _itemList

    var mainModel: CharacterModel? = null

    fun setActor(model: CharacterModel) {
        mainModel = model
    }

    fun getActor() = mainModel ?: checkNotNull("not init actor model")

    fun getList() {
        viewModelScope.launch {
            val list = getListEpisode()
            _itemList.postValue(list)
        }
    }

    private suspend fun getListEpisode(): List<Episode> {
        var listEpisodeServer = emptyList<Episode>().toMutableList()
        listEpisodeServer.add(Episode(0, "", ""))
        mainModel?.episode?.forEach { url ->
            val id = url.split('/').last().toInt()
            val resultEpisode = movieRepository.getEpisodeById(id)
            if (resultEpisode.id != 0)
                listEpisodeServer.add(resultEpisode)
        }
        return listEpisodeServer
    }

}