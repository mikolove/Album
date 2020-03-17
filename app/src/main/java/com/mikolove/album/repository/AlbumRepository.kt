package com.mikolove.album.repository

import androidx.lifecycle.LiveData
import com.mikolove.album.data.AlbumApi
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber

class AlbumRepository(private val database : AlbumDatabase) {

    suspend fun isOnlineDataFetched() : Int{
        return withContext(Dispatchers.IO){
            database.AlbumDao().isOnlineDataFetched()
        }
    }

    suspend fun getAllAlbum() : List<Item>{
        return withContext(Dispatchers.IO){
            database.AlbumDao().getAllAlbum().withHeader()
        }
    }

    suspend fun getAlbumById(id_album : Int) : List<AlbumEntity>{
        return withContext(Dispatchers.IO){
            database.AlbumDao().getAlbumById(id_album)
        }
    }

    suspend fun getDetailById(id : Int) : AlbumItem{
        return withContext(Dispatchers.IO){
            database.AlbumDao().getDetailById(id).asItem()
        }
    }
    suspend fun getOnlineAlbumData(){
        withContext(Dispatchers.IO){
            try {
                var getAlbumDataDeferred = AlbumApi.retrofitService.getAlbumData().await()
                getAlbumDataDeferred?.let {
                    database.AlbumDao().insertAll(getAlbumDataDeferred.asAlbum())
                }
            }catch (e : Exception){
                Timber.i("Exception on retrieving data")
            }
        }
    }
}