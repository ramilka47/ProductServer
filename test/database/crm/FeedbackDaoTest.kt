package com.flower.server.database.crm

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.FeedbackDao
import com.flower.server.database.dao.impl.CrmDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class FeedbackDaoTest {

    private val dao : FeedbackDao = CrmDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addFeedback(1L, 1L, "", null, 1L, 1L) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            val items = dao.getAllFeedbacks()
            items.forEach {
                assert(dao.deleteFeedback(it.id))
            }
        }
    }

    @Test
    fun updateFeedback(){
        runBlocking {
            val item = dao.getAllFeedbacks().firstOrNull()
            assert(item != null)
            assert(dao.updateFeedback(item!!.id, "", updateDate = 2L))
        }
    }

    @Test
    fun getFeedback(){
        runBlocking {
            val item = dao.getAllFeedbacks().firstOrNull()
            assert(item != null)
            assert(dao.getFeedback(item!!.id) != null)
        }
    }

    @Test
    fun getAllFeedbacks(){
        runBlocking {
            assert(dao.getAllFeedbacks().isNotEmpty())
        }
    }

    @Test
    fun getAllFeedbacksByProductId(){
        runBlocking {
            val item = dao.getAllFeedbacks().firstOrNull()
            assert(item != null)
            assert(dao.getAllFeedbacksByProductId(item!!.productId!!).isNotEmpty())
        }
    }

}