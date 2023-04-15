package com.flower.server.database.bonus_club

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.BonusCountDao
import com.flower.server.database.dao.impl.BonusClubDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class BonusCountDaoTest {

    private val dao : BonusCountDao = BonusClubDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addBonusCount(1L, 2.0) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            assert(dao.deleteBonusCount(1L))
        }
    }

    @Test
    fun updateBonusCount(){
        runBlocking {
            assert(dao.updateBonusCount(1L, 3.0))
        }
    }

    @Test
    fun getBonusCount(){
        runBlocking {
            assert(dao.getBonusCount(1L) != null)
        }
    }

}