package com.mikolove.album.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.entities.Album
import com.mikolove.album.repository.AlbumRepository
import kotlinx.coroutines.launch

class DetailViewModel(val database : AlbumDatabase, val id_album: Int) : ViewModel() {

    private val albumRepository = AlbumRepository(database)

    private val _album = MutableLiveData<List<Album>>()
    val album : LiveData<List<Album>>
        get() = _album

    init {
      loadAlbum()
    }

    private fun loadAlbum(){
        viewModelScope.launch {
            _album.value = albumRepository.getAlbumById(id_album)
        }
    }
}