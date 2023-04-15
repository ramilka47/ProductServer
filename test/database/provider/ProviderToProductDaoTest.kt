package com.flower.server.database.provider

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.ProviderToProductDao
import com.flower.server.database.dao.impl.ProviderDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProviderToProductDaoTest {

    private val dao : ProviderToProductDao = ProviderDaoImpl()

    @Before
    fun before() {
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addProviderToProduct(1L, 1L) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            val items = dao.getAllProviderToProduct()
            items.forEach {
                assert(dao.deleteProviderToProduct(it.id))
            }
        }
    }

    @Test
    fun getAllProviderToProduct(){
        runBlocking {
            assert(dao.getAllProviderToProduct().isNotEmpty())
        }
    }

    @Test
    fun getAllProviderToProductByProductId(){
        runBlocking {
            val item = dao.getAllProviderToProduct().firstOrNull()
            assert(item != null)
            assert(dao.getAllProviderToProductByProductId(item!!.productId).isNotEmpty())
        }
    }

    @Test
    fun getAllProviderToProductByProviderId(){
        runBlocking {
            val item = dao.getAllProviderToProduct().firstOrNull()
            assert(item != null)
            assert(dao.getAllProviderToProductByProductId(item!!.providerId).isNotEmpty())
        }
    }
}