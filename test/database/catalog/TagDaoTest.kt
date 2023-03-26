package com.flower.server.database.catalog

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.TagDao
import com.flower.server.database.dao.impl.CatalogDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class TagDaoTest {

    private val dao : TagDao = CatalogDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val genre = dao.addTag("name")
            assert(genre != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            dao.getAllTags().filter { it.name == "name" || it.name == "name1" }.map { it.id }.forEach {
                assert( dao.deleteTag(it))
            }
        }
    }

    @Test
    fun addGenre(){
        runBlocking {
            val genre = dao.addTag("name")
            assert(genre != null)
        }
    }

    @Test
    fun updateGenre(){
        runBlocking {
            val genre = dao.getAllTags().firstOrNull()
            assert(genre != null)
            val res = dao.updateTag(genre!!.id, "name1")
            assert(res)
        }
    }

    @Test
    fun deleteGenre(){
        runBlocking {
            val genre = dao.getAllTags().firstOrNull()
            assert(genre != null)
            val result = dao.deleteTag(genre!!.id)
            assert(result)
        }
    }

    @Test
    fun getAllGenres(){
        runBlocking {
            val genre = dao.getAllTags()
            assert(genre.isNotEmpty())
        }
    }

    @Test
    fun getAllGenresByIds(){
        runBlocking {
            val genre = dao.getAllTags().firstOrNull()
            assert(genre != null)
            val result = dao.getAllTagsById(listOf(genre!!.id))
            assert(result.isNotEmpty())
        }
    }


}