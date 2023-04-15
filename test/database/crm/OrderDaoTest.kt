package com.flower.server.database.crm

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.OrderDao
import com.flower.server.database.dao.impl.CrmDaoImpl
import com.flower.server.database.models.crm.CreateStatus
import com.flower.server.database.models.crm.DeliveryStatus
import com.flower.server.database.models.crm.SupplyStatus
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class OrderDaoTest {

    private val dao : OrderDao = CrmDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addOrder(1L, 1L, "", "", SupplyStatus.DONE, CreateStatus.DONE, DeliveryStatus.DONE, 1L, 1L) != null)
        }
    }

    @Test
    fun updateOrder(){
        runBlocking {
            val item = dao.getAllOrder().firstOrNull()
            assert(item != null)
            assert(dao.updateOrder(item!!.id, createStatus = CreateStatus.CANCELED, updateDate = 2L))
        }
    }

    @Test
    fun getOrder(){
        runBlocking {
            val item = dao.getAllOrder().firstOrNull()
            assert(item != null)
            assert(dao.getOrder(item!!.id) != null)
        }
    }

    @Test
    fun getAllOrderByCustomerId(){
        runBlocking {
            val item = dao.getAllOrder().firstOrNull()
            assert(item != null)
            assert(dao.getAllOrderByCustomerId(item!!.customerId).isNotEmpty())
        }
    }

    @Test
    fun getAllOrder(){
        runBlocking {
            assert(dao.getAllOrder().isNotEmpty())
        }
    }

}