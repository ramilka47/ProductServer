package com.flower.server.database.crm

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.CustomerDao
import com.flower.server.database.dao.impl.CrmDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CustomerDaoTest {

    private val dao : CustomerDao = CrmDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addCustomer("name") != null)
        }
    }

    @Test
    fun updateCustomer(){
        runBlocking {
            val tmp = dao.getAllCustomer().firstOrNull()
            assert(tmp != null)
            val result = dao.updateCustomer(tmp!!.id, "name2")
            assert(result)
        }
    }

    @Test
    fun getCustomer(){
        runBlocking {
            val tmp = dao.getAllCustomer().firstOrNull()
            assert(tmp != null)
            val result = dao.getCustomer(tmp!!.id)
            assert(result != null)
        }
    }

    @Test
    fun getCustomerByName(){
        runBlocking {
            val tmp = dao.getAllCustomer().firstOrNull()
            assert(tmp != null)
            val result = dao.getCustomer(tmp!!.name!!)
            assert(result != null)
        }
    }

    @Test
    fun getAllCustomer(){
        runBlocking {
            assert(dao.getAllCustomer().isNotEmpty())
        }
    }

}