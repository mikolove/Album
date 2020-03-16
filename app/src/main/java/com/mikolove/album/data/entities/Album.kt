package com.mikolove.album.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.mikolove.album.data.AlbumDatabase

@Entity(
    tableName = AlbumDatabase.albumTableName,
    primaryKeys = ["id"],
    indices = [Index("id")]
)
data class Album (
    @ColumnInfo(name="id")
    var id: Int,
    @ColumnInfo(name="albumId")
    var albumId: Int,
    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="url")
    var url: String,
    @ColumnInfo(name="thumbnailUrl")
    var thumbnailUrl: String
 )