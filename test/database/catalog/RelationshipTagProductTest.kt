package com.flower.server.database.catalog

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.RelationshipProductToTagDao
import com.flower.server.database.dao.impl.CatalogDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class RelationshipTagProductTest {

    private val dao : RelationshipProductToTagDao = CatalogDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val relationship = dao.addProductInTag(1, 1)
            assert(relationship)
        }
    }

    @After
    fun after(){
        runBlocking {
            dao.getAllRelationshipByTag(1).map { it.id }.forEach {
                assert( dao.deleteRelationshipTagToProduct(it))
            }
        }
    }

    @Test
    fun addProductInTag(){
        runBlocking {
            val result = dao.addProductInTag(1, 1)
            assert(result)
        }
    }

    @Test
    fun addTagInProduct(){
        runBlocking {
            val result = dao.addTagInProduct(1, 1)
            assert(result)
        }
    }

    @Test
    fun deleteRelationshipTagToProduct(){
        runBlocking {
            val result = dao.deleteRelationshipTagToProduct(1)
            assert(result)
        }
    }

    @Test
    fun getAllRelationshipByTag(){
        runBlocking {
            val result = dao.getAllRelationshipByTag(1)
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun getAllRelationshipTagByProduct(){
        runBlocking {
            val result = dao.getAllRelationshipByTag(1)
            assert(result.isNotEmpty())
        }
    }

}