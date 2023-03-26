package com.flower.server.database.catalog

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.GenreDao
import com.flower.server.database.dao.impl.CatalogDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GenreDaoTest {

    private val dao : GenreDao = CatalogDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val genre = dao.addGenre("name")
            assert(genre != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            dao.getAllGenres().filter { it.name == "name" || it.name == "name1" }.map { it.id }.forEach {
                assert( dao.deleteGenre(it))
            }
        }
    }

    @Test
    fun addGenre(){
        runBlocking {
            val genre = dao.addGenre("name")
            assert(genre != null)
        }
    }

    @Test
    fun updateGenre(){
        runBlocking {
            val genre = dao.getAllGenres().firstOrNull()
            assert(genre != null)
            val res = dao.updateGenre(genre!!.id, "name1")
            assert(res)
        }
    }

    @Test
    fun deleteGenre(){
        runBlocking {
            val genre = dao.getAllGenres().firstOrNull()
            assert(genre != null)
            val result = dao.deleteGenre(genre!!.id)
            assert(result)
        }
    }

    @Test
    fun getAllGenres(){
        runBlocking {
            val genre = dao.getAllGenres()
            assert(genre.isNotEmpty())
        }
    }

    @Test
    fun getAllGenresByIds(){
        runBlocking {
            val genre = dao.getAllGenres().firstOrNull()
            assert(genre != null)
            val result = dao.getAllGenresByIds(listOf(genre!!.id))
            assert(result.isNotEmpty())
        }
    }

}