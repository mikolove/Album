package com.mikolove.album.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikolove.album.data.AlbumDatabase

class AlbumViewModelFactory (private val dataSource: AlbumDatabase) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
            return AlbumViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}