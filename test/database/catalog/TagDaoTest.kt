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
            val tag = dao.addTag("name")
            assert(tag != null)
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
    fun getTag(){
        runBlocking {
            val tag = dao.getAllTags().firstOrNull()
            assert(tag != null)
            assert(dao.getTag(tag!!.id) != null)
        }
    }

    @Test
    fun addTag(){
        runBlocking {
            val tag = dao.addTag("name")
            assert(tag != null)
        }
    }

    @Test
    fun updateTag(){
        runBlocking {
            val tag = dao.getAllTags().firstOrNull()
            assert(tag != null)
            val res = dao.updateTag(tag!!.id, "name1")
            assert(res)
        }
    }

    @Test
    fun deleteTag(){
        runBlocking {
            val tag = dao.getAllTags().firstOrNull()
            assert(tag != null)
            val result = dao.deleteTag(tag!!.id)
            assert(result)
        }
    }

    @Test
    fun getAllTags(){
        runBlocking {
            val tag = dao.getAllTags()
            assert(tag.isNotEmpty())
        }
    }

    @Test
    fun getAllTagsByIds(){
        runBlocking {
            val tag = dao.getAllTags().firstOrNull()
            assert(tag != null)
            val result = dao.getAllTagsByIds(listOf(tag!!.id))
            assert(result.isNotEmpty())
        }
    }


}