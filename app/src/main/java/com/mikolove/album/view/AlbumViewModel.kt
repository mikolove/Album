package com.mikolove.album.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.entities.Item
import com.mikolove.album.repository.AlbumRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class AlbumViewModel(private val database : AlbumDatabase) : ViewModel() {

    private val albumRepository = AlbumRepository(database)

    private val _allAlbum = MutableLiveData<List<Item>>()
    val allAlbum : LiveData<List<Item>>
        get() = _allAlbum

    init {
        loadAlbum()
        loadAllAlbum()
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

    private fun loadAllAlbum(){
        viewModelScope.launch {
            _allAlbum.value = albumRepository.getAllAlbum()
        }
    }

}