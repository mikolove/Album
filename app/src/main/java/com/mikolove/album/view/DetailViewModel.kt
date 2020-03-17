package com.mikolove.album.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.entities.AlbumItem
import com.mikolove.album.repository.AlbumRepository
import kotlinx.coroutines.launch

class DetailViewModel(database : AlbumDatabase, val id: Int) : ViewModel() {


    private val albumRepository = AlbumRepository(database)

    private val _detail = MutableLiveData<AlbumItem>()
    val detail : LiveData<AlbumItem>
        get() = _detail

    private fun loadAlbum(){
        viewModelScope.launch {
            _detail.value = albumRepository.getDetailById(id)
        }
    }

    init {
        loadAlbum()
    }
}