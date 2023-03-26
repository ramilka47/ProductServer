package com.flower.server.database.storage

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.impl.StockroomDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CountDaoTest {

    private val dao : CountDao = StockroomDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            try {
                val result = dao.addCount(1)
                assert(result != null)
            }catch (e : Exception){
                e.message
            }
        }
    }

    @Test
    fun updateCount(){
        runBlocking {
            val result = dao.updateCount(1, 4)
            assert(result)
        }
    }

    @Test
    fun getCount(){
        runBlocking {
            val result = dao.getCount(1)
            assert(result != null)
        }
    }

}