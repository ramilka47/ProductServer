package com.flower.server.database.bonus_club

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.BonusCardDao
import com.flower.server.database.dao.impl.BonusClubDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class BonusCardDaoTest {

    private val dao : BonusCardDao = BonusClubDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addBonusCard("name", false) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            dao.getAllBonusCards().forEach {
                assert(dao.deleteBonusCard(it.id))
            }
        }
    }

    @Test
    fun updateBonusCard(){
        runBlocking {
            val item = dao.getAllBonusCards().firstOrNull()
            assert(item != null)
            assert(dao.updateBonusCard(item!!.id, active = true))
        }
    }

    @Test
    fun getBonusCard(){
        runBlocking {
            val item = dao.getAllBonusCards().firstOrNull()
            assert(item != null)
            assert(dao.getBonusCard(item!!.id) != null)
        }
    }

    @Test
    fun getAllBonusCards(){
        runBlocking {
            assert(dao.getAllBonusCards().isNotEmpty())
        }
    }
}