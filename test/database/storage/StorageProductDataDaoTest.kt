package com.flower.server.database.storage

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.StorageProductDataDao
import com.flower.server.database.dao.impl.StockroomDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class StorageProductDataDaoTest {

    private val dao : StorageProductDataDao = StockroomDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            try {
                val data = dao.addStorageProductData(1, 30.0, 0.5f, "12")
                assert(data != null)
            }catch (e : Exception){
                e.message
            }
        }
    }

    @Test
    fun addStorageProductData(){
        runBlocking {
            try {
                val data = dao.addStorageProductData(1, 30.0, 0.5f, "")
                assert(data != null)
            }catch (e : Exception){
                e.message
            }
        }
    }

    @Test
    fun updateStorageProductData(){
        runBlocking {
            val data = dao.updateStorageProductData(1, 35.0, 0.5f, "")
            assert(data)
        }
    }

    @Test
    fun getStorageProductDataByProductId(){
        runBlocking {
            val data = dao.getStorageProductData(1)
            assert(data != null)
        }
    }

    @Test
    fun getStorageProductDataByUnicode(){
        runBlocking {
            val data = dao.getStorageProductData("12")
            assert(data != null)
        }
    }

}