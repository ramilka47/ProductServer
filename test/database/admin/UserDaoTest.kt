package com.flower.server.database.admin

import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.UserDao
import com.flower.server.database.dao.impl.AdminDaoImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDaoTest {

    private val dao : UserDao = AdminDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(dao.addUser("name", "login", "password", 1, "token") != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            dao.getAllUsers().map { it.id }.forEach {
                dao.deleteUser(it)
            }
        }
    }

    @Test
    fun updateUser(){
        runBlocking {
            val user = dao.getAllUsers().firstOrNull()
            assert(user != null)
            assert(dao.updateUser(user!!.id, "name1"))
        }
    }

    @Test
    fun deleteUser(){
        runBlocking {
            val user = dao.getAllUsers().firstOrNull()
            assert(user != null)
            assert(dao.deleteUser(user!!.id))
        }
    }

    @Test
    fun getUser(){
        runBlocking {
            val user = dao.getAllUsers().firstOrNull()
            assert(user != null)
            assert(dao.getUser(user!!.id) != null)
        }
    }

    @Test
    fun getUserByName(){
        runBlocking {
            val user = dao.getAllUsers().firstOrNull()
            assert(user != null)
            assert(dao.getUserByName(user!!.name) != null)
        }
    }

    @Test
    fun getUserByLogin(){
        runBlocking {
            val user = dao.getAllUsers().firstOrNull()
            assert(user != null)
            assert(dao.getUserByLogin(user!!.login) != null)
        }
    }

    @Test
    fun getUserByToken(){
        runBlocking {
            val user = dao.getAllUsers().firstOrNull()
            assert(user != null)
            assert(dao.getUserByToken(user!!.token) != null)
        }
    }

    @Test
    fun getAllUsers(){
        runBlocking {
            assert(dao.getAllUsers().isNotEmpty())
        }
    }


}