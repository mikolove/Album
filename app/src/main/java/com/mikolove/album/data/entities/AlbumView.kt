package com.mikolove.album.data.entities

import androidx.room.DatabaseView
import com.mikolove.album.data.AlbumDatabase

@DatabaseView("SELECT ${AlbumDatabase.albumTableName}.albumId, COUNT(*) as nbElement  FROM ${AlbumDatabase.albumTableName} GROUP BY ${AlbumDatabase.albumTableName}.albumId")
data class AlbumView(
    val albumId : Int,
    val nbElement : Int
)