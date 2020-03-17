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

class AlbumViewModel(database : AlbumDatabase) : ViewModel() {

    private val albumRepository = AlbumRepository(database)

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
        get() = _loading

    private val _allAlbum = MutableLiveData<List<Item>>()
    val allAlbum : LiveData<List<Item>>
        get() = _allAlbum

    fun getLoadingState() = loading.value

    init {
        _loading.value = false
        loadAlbum()
    }

    fun refreshAlbum(){
        loadAlbum(true)
    }
    private fun loadAlbum( forceReset : Boolean = false){
        viewModelScope.launch {
            _loading.value = true
            if(albumRepository.isOnlineDataFetched() == 0 || forceReset){
                Timber.i("No data fecth it")
                try {
                    albumRepository.getOnlineAlbumData()
                }catch (e : Exception){
                    Timber.i("Exception view model loading data %s",e)
                }
            }
            _allAlbum.value = albumRepository.getAllAlbum()
            _loading.value = false
        }
    }

}