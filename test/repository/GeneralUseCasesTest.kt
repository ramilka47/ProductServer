package com.flower.server.repository

import com.flower.server.core.LoginPasswordCheckChars
import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.impl.AdminDaoImpl
import com.flower.server.helper.DEVELOPER_LEVEL
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

class GeneralUseCasesTest {

    companion object{
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val NAME = "name"
    }

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val token = generator.generateToken()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val user = userDao.addUser(NAME, LOGIN, PASSWORD, DEVELOPER_LEVEL, token)
            assert(user != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            userDao.getAllUsers().forEach {
                assert(userDao.deleteUser(it.id))
            }
        }
    }
}