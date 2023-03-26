package com.flower.server.database.crm

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.RelationshipCustomerToOperationDao
import com.flower.server.database.dao.impl.CrmDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RelationshipCustomerToOperationDaoTest {

    private val dao : RelationshipCustomerToOperationDao = CrmDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addCustomerToOperation(1, 1) != null)
        }
    }

    @Test
    fun getCustomerToOperation(){
        runBlocking {
            val tmp = dao.getAllCustomerToOperation().firstOrNull()
            assert(tmp != null)
            val customerToOperation = dao.getCustomerToOperation(tmp!!.id)
            assert(customerToOperation != null)
        }
    }

    @Test
    fun getAllCustomerToOperation(){
        runBlocking {
            val result = dao.getAllCustomerToOperation()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun getAllCustomerToOperationByCustomerId(){
        runBlocking {
            val tmp = dao.getAllCustomerToOperation().firstOrNull()
            assert(tmp != null)
            val result = dao.getAllCustomerToOperationByCustomerId(tmp!!.customerId)
            assert(result.isNotEmpty())
        }
    }

}