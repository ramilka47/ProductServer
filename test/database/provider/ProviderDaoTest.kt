package com.flower.server.database.provider

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.ProviderDao
import com.flower.server.database.dao.impl.ProviderDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProviderDaoTest {

    private val dao : ProviderDao = ProviderDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addProvider("name", "address","description", "phone") != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            val items = dao.getAllProvider()
            items.forEach {
                assert(dao.deleteProvider(it.id))
            }
        }
    }

    @Test
    fun updateProvider(){
        runBlocking {
            val item = dao.getAllProvider().firstOrNull()
            assert(item != null)
            assert(dao.updateProvider(item!!.id, phone = "new phone"))
        }
    }

    @Test
    fun getAllProvider(){
        runBlocking {
            val item = dao.getAllProvider()
            assert(item.isNotEmpty())
        }
    }

    @Test
    fun getProvider(){
        runBlocking {
            val item = dao.getAllProvider().firstOrNull()
            assert(item != null)
            assert(dao.getProvider(item!!.id) != null)
        }
    }

}