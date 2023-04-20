package com.flower.server.repository

import com.flower.server.repository.bonus_club.GetBonusCardByLoginUseCase
import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.core.level_checker.CheckAdminLevel
import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.impl.AdminDaoImpl
import com.flower.server.database.dao.impl.BonusClubDaoImpl
import com.flower.server.helper.DEVELOPER_LEVEL
import com.flower.server.helper.USER_LEVEL
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.bonus_club.*
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.bonus_club.AddBonusCardRequest
import com.flower.server.web.models.request.bonus_club.GetBonusCardByLoginRequest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BonusClubUseCasesTest {

    companion object{
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val NAME = "name"
    }

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val token = generator.generateToken()
    private val token_user = generator.generateToken()

    private val bonusClub = BonusClubDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val user = userDao.addUser(NAME, LOGIN, PASSWORD, DEVELOPER_LEVEL, token)
            assert(userDao.addUser("user name", "user_login", "user_password", USER_LEVEL, token_user) != null)
            val bonusCard = bonusClub.addBonusCard(user!!.login, false)
            assert(user != null)
            assert(bonusCard != null)
            assert(bonusClub.addBonusCount(bonusCard!!.id, 5.0) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            userDao.getAllUsers().forEach {
                assert(userDao.deleteUser(it.id))
            }
            bonusClub.getAllBonusCards().forEach {
                assert(bonusClub.deleteBonusCard(it.id))
            }
        }
    }

    @Test
    fun addBonusCardUseCase(){
        val useCase = AddBonusCardUseCase(bonusClub, userDao, CheckAdminLevel, AddBonusCountUseCase(bonusClub))

        runBlocking {
            assert(useCase.getResponse(AddBonusCardRequest("for any user", false), token).bonusCard.id != null)
        }

        runBlocking {
            AddBonusCardUseCase(bonusClub, userDao, addBonusCountUseCase = AddBonusCountUseCase(bonusClub))
                .getResponse(AddBonusCardRequest("for any user", false), token_user)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(AddBonusCardRequest("for any user", false))
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                assert(useCase.getResponse(AddBonusCardRequest(active =  false), token_user).bonusCard.id != null)
            }
        }
    }

    @Test
    fun activateBonusCardUseCase(){
        val useCase = ActivateBonusCardUseCase(bonusClub)

        runBlocking {
            val card = bonusClub.getAllBonusCards().first()
            assert(useCase.execute(card.id, true))
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE, true)
            }
        }
    }

    @Test
    fun deleteBonusCardUseCase(){
        val useCase = DeleteBonusCardUseCase(bonusClub, DeleteBonusCountUseCase((bonusClub)))

        runBlocking {
            val card = bonusClub.getAllBonusCards().first()
            assert(useCase.execute(card.id))
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE)
            }
        }
    }

    @Test
    fun updateBonusCountUseCase(){
        val useCase = UpdateBonusCountUseCase(bonusClub)
        runBlocking {
            val card = bonusClub.getAllBonusCards().first()
            assert(useCase.execute(card.id, 90.0))
        }
    }

    @Test
    fun getBonusCardUseCase(){
        val useCase = GetBonusCardUseCase(bonusClub, userDao, GetBonusCountUseCase(bonusClub))

        runBlocking {
            assert(useCase.getResponse(EmptyRequest, token).bonusCard != null)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(EmptyRequest)
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(EmptyRequest, token_user)
            }
        }
    }

    @Test
    fun getBonusCardByLogin(){
        val useCase = GetBonusCardByLoginUseCase(bonusClub, userDao, CheckAdminLevel, GetBonusCountUseCase(bonusClub))
        val useCaseWithoutChecker = GetBonusCardByLoginUseCase(bonusClub, userDao, getBonusCountUseCase = GetBonusCountUseCase(bonusClub))

        runBlocking {
            val user = userDao.getUserByToken(token)!!
            assert(useCase.getResponse(GetBonusCardByLoginRequest(user.login), token).bonusCard != null)
        }

        runBlocking {
            val user = userDao.getUserByToken(token)!!
            assert(useCaseWithoutChecker.getResponse(GetBonusCardByLoginRequest(user.login), token).bonusCard != null)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(GetBonusCardByLoginRequest("login"))
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(GetBonusCardByLoginRequest("login"), token_user)
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCaseWithoutChecker.getResponse(GetBonusCardByLoginRequest("login"), token_user)
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCaseWithoutChecker.getResponse(GetBonusCardByLoginRequest("login"))
            }
        }
    }

    @Test
    fun getBonusCountUseCase(){
        val useCase = GetBonusCountUseCase(bonusClub)

        runBlocking {
            val card = bonusClub.getAllBonusCards().first()
            assert(useCase.execute(card.id) != null)
        }
    }

    @Test
    fun getAllBonusCardUseCase(){
        val useCase = GetAllBonusCardUseCase(bonusClub, userDao, CheckAdminLevel, GetBonusCountUseCase(bonusClub))

        runBlocking {
            assert(useCase.getResponse(EmptyRequest, token).bonusCards.isNotEmpty())
        }
    }

}