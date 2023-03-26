package com.flower.server.database.catalog

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.RelationshipProductToGenreDao
import com.flower.server.database.dao.impl.CatalogDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class RelationshipGenreProductTest {

    private val dao : RelationshipProductToGenreDao = CatalogDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val relationship = dao.addProductInGenre(1, 1)
            assert(relationship)
        }
    }

    @After
    fun after(){
        runBlocking {
            dao.getAllRelationshipByGenre(1).map { it.id }.forEach {
                assert( dao.deleteRelationshipGenreToProduct(it))
            }
        }
    }

    @Test
    fun addGenreInTag(){
        runBlocking {
            val result = dao.addProductInGenre(1, 1)
            assert(result)
        }
    }

    @Test
    fun addGenreInProduct(){
        runBlocking {
            val result = dao.addGenreInProduct(1, 1)
            assert(result)
        }
    }

    @Test
    fun deleteRelationshipGenreToProduct(){
        runBlocking {
            val result = dao.deleteRelationshipGenreToProduct(1)
            assert(result)
        }
    }

    @Test
    fun getAllRelationshipByGenre(){
        runBlocking {
            val result = dao.getAllRelationshipByGenre(1)
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun getAllRelationshipGenreByProduct(){
        runBlocking {
            val result = dao.getAllRelationshipGenreByProduct(1)
            assert(result.isNotEmpty())
        }
    }


}