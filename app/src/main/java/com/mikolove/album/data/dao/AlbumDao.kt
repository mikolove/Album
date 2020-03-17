package com.mikolove.album.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.entities.AlbumEntity

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listAlbum: List<AlbumEntity>)

    @Query("DELETE FROM ${AlbumDatabase.albumTableName}")
    fun clearAll()

    @Query("SELECT * FROM ${AlbumDatabase.albumTableName}")
    fun getAllAlbum() : List<AlbumEntity>

    @Query("SELECT count(*) as isFetched FROM ${AlbumDatabase.albumTableName} LIMIT 1")
    fun isOnlineDataFetched() : Int

    @Query("SELECT * FROM ${AlbumDatabase.albumTableName} WHERE id = :id")
    fun getDetailById(id : Int) : AlbumEntity

}