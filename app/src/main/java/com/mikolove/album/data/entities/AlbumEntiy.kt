package com.mikolove.album.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.mikolove.album.data.AlbumDatabase

/*
    Album Item class for database element
 */

@Entity(
    tableName = AlbumDatabase.albumTableName,
    primaryKeys = ["id"],
    indices = [Index("id")]
)
data class AlbumEntity(
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

fun AlbumEntity.asItem() = AlbumItem(
    id = id,
    albumId = albumId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl
)

fun List<AlbumEntity>.withHeader() : List<Item>{
    val listWithHeader = mutableListOf<Item>()
    var actualHeader = 0
    forEach {it->
        if(it.albumId != actualHeader){
            listWithHeader.add(HeaderItem(it.albumId))
        }
        actualHeader = it.albumId
        listWithHeader.add(AlbumItem(it.id,it.albumId,it.title,it.url,it.thumbnailUrl))
    }
    return listWithHeader
}