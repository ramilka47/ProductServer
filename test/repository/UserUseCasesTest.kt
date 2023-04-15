package com.flower.server.repository

import com.flower.server.core.LoginPasswordCheckChars
import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.core.level_checker.CheckDevelopLevel
import com.flower.server.core.level_checker.CheckLevel
import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.impl.AdminDaoImpl
import com.flower.server.helper.DEVELOPER_LEVEL
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.user.*
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.user.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class UserUseCasesTest {

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val token = generator.generateToken()
    private val loginPasswordCheckChars = LoginPasswordCheckChars()

    companion object{
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val NAME = "name"
    }

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            assert(userDao.addUser(NAME, LOGIN, PASSWORD, DEVELOPER_LEVEL, token) != null)
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

    @Test
    fun signUp(){
        val signUpUseCase = SignUpUseCase(userDao, generator, loginPasswordCheckChars)
        runBlocking {
            val response = signUpUseCase.getResponse(SignUpRequest("myName", "myLogin", "myPassword"))
            assert(response.token.isNotEmpty())
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                signUpUseCase.getResponse(SignUpRequest("myName", "myLogin", "myPassword"))
            }
        }
    }

    @Test
    fun signIn(){
        val signInUseCase = SignInUseCase(userDao, generator)
        runBlocking {
            assert(signInUseCase.getResponse(SignInRequest(LOGIN, PASSWORD)).name == NAME)
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                signInUseCase.getResponse(SignInRequest("bad Login", ""))
            }
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                signInUseCase.getResponse(SignInRequest(LOGIN, "bad password"))
            }
        }
    }

    @Test
    fun getUserByToken(){
        val getUserByTokenUseCase = GetUserByTokenUseCase(userDao)
        runBlocking {
            assert(getUserByTokenUseCase.getResponse(EmptyRequest, token).token == token)
        }

        assertThrows(BadUserTokenException::class.java){
            runBlocking {
                getUserByTokenUseCase.getResponse(EmptyRequest)
            }
        }
    }

    @Test
    fun getUserById(){
        val getUserByIdUseCase = GetUserByIdUseCase(userDao, CheckLevel(CheckDevelopLevel))
        runBlocking {
            val user = userDao.getUserByToken(token)
            assert(user != null)
            assert(getUserByIdUseCase.getResponse(IdRequest(user!!.id), token).token == token)
        }

        assertThrows(BadUserTokenException::class.java){
            runBlocking {
                getUserByIdUseCase.getResponse(IdRequest(Long.MAX_VALUE))
            }
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                val user = userDao.getUserByToken(token)
                assert(user != null)
                getUserByIdUseCase.getResponse(IdRequest(user!!.id))
            }
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                val user = userDao.addUser("newName", "newLogin", "newPassword", 3, generator.generateToken())
                val developUser = userDao.getUserByToken(token)
                assert(user != null)
                getUserByIdUseCase.getResponse(IdRequest(developUser!!.id), user!!.token).token.isNotEmpty()
            }
        }
    }

    @Test
    fun updateUserLevel(){
        val updateUserLevelUseCase = UpdateUserLevelUseCase(userDao, CheckLevel(CheckDevelopLevel))

        runBlocking {
            val user = userDao.getUserByToken(token)
            assert(user != null)
            assert(updateUserLevelUseCase.getResponse(UpdateUserLevelRequest(user!!.id, 6), token).level == 6)
        }

        assertThrows(BadUserTokenException::class.java){
            runBlocking {
                val user = userDao.getUserByToken(token)
                assert(user != null)
                updateUserLevelUseCase.getResponse(UpdateUserLevelRequest(user!!.id, 6))
            }
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                val user = userDao.addUser("newName", "newLogin", "newPassword", 3, generator.generateToken())
                assert(user != null)
                updateUserLevelUseCase.getResponse(UpdateUserLevelRequest(user!!.id, 6), user.token)
            }
        }
    }

    @Test
    fun updateUserName(){
        val updateUserNameUseCase = UpdateUserNameUseCase(userDao)
        runBlocking {
            val user = userDao.getUserByToken(token)
            assert(user != null)
            assert(updateUserNameUseCase.getResponse(UpdateUserNameRequest(user!!.id, "newName"), token).name == "newName")
        }

        assertThrows(BadUserTokenException::class.java){
            runBlocking {
                assert(updateUserNameUseCase.getResponse(UpdateUserNameRequest(50, "newName")).name == "newName")
            }
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                assert(updateUserNameUseCase.getResponse(UpdateUserNameRequest(50, "newName"), token).name == "newName")
            }
        }
    }

    @Test
    fun updateUserPassword(){
        val updateUserPasswordUseCase = UpdateUserPasswordUseCase(userDao)

        runBlocking {
            val user = userDao.getUserByToken(token)
            assert(user != null)
            assert(updateUserPasswordUseCase.getResponse(UpdatePasswordRequest(user!!.id, "newPassword"), token).password == "newPassword")
        }

        assertThrows(BadUserTokenException::class.java){
            runBlocking {
                updateUserPasswordUseCase.getResponse(UpdatePasswordRequest(123, "newPassword"))
            }
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                updateUserPasswordUseCase.getResponse(UpdatePasswordRequest(123, "newPassword"), token)
            }
        }
    }

    @Test
    fun getAllUser(){
        val getAllUserUseCase = GetAllUserUseCase(userDao, CheckLevel(CheckDevelopLevel))

        runBlocking {
            assert(getAllUserUseCase.getResponse(EmptyRequest, token).users.isNotEmpty())
        }

        assertThrows(BadUserTokenException::class.java){
            runBlocking {
                assert(getAllUserUseCase.getResponse(EmptyRequest).users.isNotEmpty())
            }
        }

        assertThrows(RequestException::class.java){
            runBlocking {
                val user = userDao.addUser("newName", "newLogin", "newPassword", 3, generator.generateToken())
                assert(user != null)
                assert(getAllUserUseCase.getResponse(EmptyRequest, user!!.token).users.isNotEmpty())
            }
        }
    }

}