package com.mikolove.album.repository

import com.mikolove.album.data.AlbumApi
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber

class AlbumRepository(private val database : AlbumDatabase) {

    suspend fun getOnlineAlbumData(){
        withContext(Dispatchers.IO){
            try {
                var getAlbumDataDeferred = AlbumApi.retrofitService.getAlbumData().await()
                getAlbumDataDeferred?.let {
                    database.albumDao().clearAll()
                    database.albumDao().insertAll(getAlbumDataDeferred.asAlbum())
                }
            }catch (e : Exception){
                Timber.i("Exception on retrieving data %s",e)
            }
        }
    }

    suspend fun isOnlineDataFetched() : Int{
        return withContext(Dispatchers.IO){
            database.albumDao().isTableEmpty()
        }
    }

    suspend fun getAllAlbum() : List<Item>{
        return withContext(Dispatchers.IO){
            database.albumDao().getAllAlbum().withHeader()
        }
    }

    suspend fun getDetailById(id : Int) : AlbumItem{
        return withContext(Dispatchers.IO){
            database.albumDao().getDetailById(id).asItem()
        }
    }
}