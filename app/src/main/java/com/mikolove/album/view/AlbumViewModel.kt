package com.mikolove.album.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.repository.AlbumRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumViewModel(private val database : AlbumDatabase) : ViewModel() {

    private val albumRepository = AlbumRepository(database)

    val albums = albumRepository.albumByGroup

    init {
        loadAlbum()
    }

    private fun loadAlbum(){
        viewModelScope.launch {
            if(albumRepository.isOnlineDataFetched() == 0){
                try {
                    albumRepository.getOnlineAlbumData()
                }catch (e : Exception){
                    Timber.i("Exception view model loading data %s",e)
                }
            }
        }
    }

}