package com.mikolove.album.data.entities

import com.squareup.moshi.Json

data class AlbumData(
    val id : Int,
    val albumId: Int,
    val title : String,
    @Json(name="url") val url : String,
    @Json(name="thumbnailUrl") val thumbnailUrl : String
)

fun List<AlbumData>.asAlbum() : List<AlbumEntity>{
    return map{
        AlbumEntity(
            id = it.id,
            albumId = it.albumId,
            title = it.title,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }
}