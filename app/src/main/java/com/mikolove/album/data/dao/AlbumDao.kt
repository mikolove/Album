package com.mikolove.album.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.entities.Album
import com.mikolove.album.data.entities.AlbumView

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listAlbum: List<Album>)

    @Query("SELECT count(*) as isFetched FROM ${AlbumDatabase.albumTableName} LIMIT 1")
    fun isOnlineDataFetched() : Int

    @Query("SELECT * FROM AlbumView")
    fun getAlbumGroupById() : LiveData<List<AlbumView>>

    @Query("SELECT * FROM ${AlbumDatabase.albumTableName} WHERE albumId = :albumId LIMIT 5")
    fun getAlbumById(albumId : Int) : List<Album>

}