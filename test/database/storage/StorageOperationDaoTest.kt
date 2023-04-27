package com.flower.server.database.storage

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.StorageOperationDao
import com.flower.server.database.dao.impl.StockroomDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class StorageOperationDaoTest {

    private val dao : StorageOperationDao = StockroomDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addBuy(listOf(1), 1, 1.0, 1) != null)
            assert(dao.addSale(listOf(1), 1, 1.0, 1) != null)
            assert(dao.addWriteOff(listOf(1), 1, 1.0, 1) != null)
            assert(dao.addReturn(listOf(1), 1, 1.0, 1) != null)
            assert(dao.addReserve(listOf(1), 1, 1.0, 1) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            dao.getAllOperation().forEach {
                assert(dao.deleteOperation(it.id))
            }
        }
    }

    @Test
    fun getAllOperation(){
        runBlocking {
            assert(dao.getAllOperation().isNotEmpty())
        }
    }

    @Test
    fun getAllBought(){
        runBlocking {
            assert(dao.getAllBought().isNotEmpty())
        }
    }

    @Test
    fun getAllSales(){
        runBlocking {
            assert(dao.getAllSales().isNotEmpty())
        }
    }

    @Test
    fun getAllWriteOffs(){
        runBlocking {
            assert(dao.getAllWriteOffs().isNotEmpty())
        }
    }

    @Test
    fun getAllReturned(){
        runBlocking {
            assert(dao.getAllReturned().isNotEmpty())
        }
    }

    @Test
    fun getAllReserved(){
        runBlocking {
            assert(dao.getAllReserved().isNotEmpty())
        }
    }

    @Test
    fun getAllOperationsByIds(){
        runBlocking {
            val result = dao.getAllOperation().firstOrNull()
            assert(result != null)
            val data = dao.getAllOperationsByIds(listOf(result!!.id))
            assert(data.isNotEmpty())
        }
    }

}