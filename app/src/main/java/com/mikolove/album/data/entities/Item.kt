package com.mikolove.album.data.entities

/*
    Item class for element used in UI
 */

sealed class Item{
    abstract val isHeader : Int
}

data class AlbumItem(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Item() {
    override val isHeader: Int = 0
}

data class HeaderItem(val id : Int) : Item(){
    override val isHeader : Int = id
}

