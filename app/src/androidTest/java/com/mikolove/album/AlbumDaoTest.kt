package com.mikolove.album

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mikolove.album.data.AlbumDatabase
import com.mikolove.album.data.dao.AlbumDao
import com.mikolove.album.data.entities.AlbumEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest {

    private lateinit var albumDao  : AlbumDao
    private lateinit var database  : AlbumDatabase
    private lateinit var albumList : List<AlbumEntity>

    @Before
    fun initDb(){

        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AlbumDatabase::class.java).build()

        albumDao = database.albumDao()

        albumList = listOf(
            AlbumEntity(id = 1,albumId = 1,title = "test insert 1",url = "https://test/123",thumbnailUrl = "https://test/123"),
            AlbumEntity(id = 2,albumId = 2,title = "test insert 2",url = "https://test/123",thumbnailUrl = "https://test/123")
        )
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAlbum(){

        albumDao.insertAll(albumList)

        val element = albumDao.getDetailById(1)

        assertThat(element , equalTo(albumList[0]))
    }

    @Test
    @Throws(Exception::class)
    fun getAllAlbum(){

        albumDao.insertAll(albumList)

        val list = albumDao.getAllAlbum()

        assertThat(list, equalTo(albumList))
    }

    @Test
    @Throws(Exception::class)
    fun clearAllAlbum(){

        albumDao.insertAll(albumList)

        albumDao.clearAll()

        val isEmpty = albumDao.isTableEmpty()

        assertThat(isEmpty, equalTo(0))

    }
}