package com.flower.server.database.catalog

import com.flower.server.database.dao.impl.CatalogDaoImpl
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.TestDatabaseFactory
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductDaoTest {

    private val dao : ProductDao = CatalogDaoImpl()

    @Before
    fun init(){
        TestDatabaseFactory.init()
        runBlocking {
            val product = dao.addProduct("name", "description")
            product?.id
        }
    }

    @After
    fun end(){
        runBlocking {
            dao.deleteAllProduct(dao.getAllProduct().filter { it.name == "name" }.map { it.id })
        }
    }

    @Test
    fun addProductTest(){
        runBlocking {
            val product = dao.addProduct(name = "name", description = "description", producer = "producer")
            assert(product != null)
        }
    }

    @Test
    fun updateProduct(){
        runBlocking {
            val product = dao.getProduct("name")
            assert(product != null)
            val result = dao.updateProduct(id = product!!.id, name = "name", description = "description_update", producer = "producer", gallery = listOf("123", "321"))
            assert(result)
        }
    }

    @Test
    fun getProduct(){
        runBlocking {
            val product1 = dao.getProduct("name")
            assert(product1 != null)
            val product = dao.getProduct(id = product1!!.id)
            assert(product != null)
        }
    }

    @Test
    fun getProductSearch(){
        runBlocking {
            val product = dao.getProduct("na")
            assert(product != null)
        }
    }

    @Test
    fun deleteProductByIdTest(){
        runBlocking {
            val product1 = dao.getProduct("name")
            assert(product1 != null)
            val product = dao.deleteProduct(product1!!.id)
            assert(product)
        }
    }

    @Test
    fun deleteProductTest(){
        runBlocking {
            val products = dao.getAllProduct()
            val result = dao.deleteProduct(products.first())
            assert(result)
        }
    }

    @Test
    fun getAllProductTest(){
        runBlocking {
            val products = dao.getAllProduct()
            assert(products.isNotEmpty())
        }
    }

}