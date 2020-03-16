package com.mikolove.album.repository

import androidx.lifecycle.LiveData
import com.mikolove.album.data.AlbumApi
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.asAlbum
import com.mikolove.album.data.entities.Album
import com.mikolove.album.data.entities.AlbumView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import timber.log.Timber

class AlbumRepository(private val database : AlbumDatabase) {

    val albumByGroup : LiveData<List<AlbumView>> = database.AlbumDao().getAlbumGroupById()

    suspend fun isOnlineDataFetched() : Int{
        return withContext(Dispatchers.IO){
            database.AlbumDao().isOnlineDataFetched()
        }
    }

    suspend fun getAlbumById(id_album : Int) : List<Album>{
        return withContext(Dispatchers.IO){
            database.AlbumDao().getAlbumById(id_album)
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