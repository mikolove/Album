package com.mikolove.album.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mikolove.album.data.dao.AlbumDao
import com.mikolove.album.data.entities.Album
import com.mikolove.album.data.entities.AlbumView

@Database(entities = [Album::class],
    views = [AlbumView::class],
    version = 1, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun AlbumDao() : AlbumDao

    companion object{

        const val albumTableName = "album"
        const val albumTableViewName = "album_view"

        @Volatile
        private var INSTANCE: AlbumDatabase? = null

        fun getInstance(context: Context): AlbumDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            AlbumDatabase::class.java,
                            "album_database"
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}