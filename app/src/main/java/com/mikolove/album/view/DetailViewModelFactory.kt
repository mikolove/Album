package com.mikolove.album.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikolove.album.data.AlbumDatabase

class DetailViewModelFactory(private val dataSource: AlbumDatabase, private val id : Int) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(dataSource,id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}