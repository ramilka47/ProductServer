package com.flower.server.repository

import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.core.level_checker.CheckEmployeeLevel
import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.impl.AdminDaoImpl
import com.flower.server.database.dao.impl.ProviderDaoImpl
import com.flower.server.helper.DEVELOPER_LEVEL
import com.flower.server.helper.USER_LEVEL
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.provider.*
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.provider.AddProviderRequest
import com.flower.server.web.models.request.provider.DeleteProviderRequest
import com.flower.server.web.models.request.provider.UpdateProviderRequest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProviderUseCasesTest {

    companion object{
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val NAME = "name"
    }

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val token = generator.generateToken()
    private val token_user = generator.generateToken()

    private val providerDao = ProviderDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val user = userDao.addUser(NAME, LOGIN, PASSWORD, DEVELOPER_LEVEL, token)
            assert(userDao.addUser("user name", "user_login", "user_password", USER_LEVEL, token_user) != null)
            val provider = providerDao.addProvider("provider name", "address", "description", "phone")
            assert(user != null)
            assert(provider != null)
            assert(providerDao.addProviderToProduct(1L, provider!!.id) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            userDao.getAllUsers().forEach {
                assert(userDao.deleteUser(it.id))
            }
            providerDao.getAllProvider().forEach {
                assert(providerDao.deleteProvider(it.id))
            }
            providerDao.getAllProviderToProduct().forEach {
                assert(providerDao.deleteProviderToProduct(it.id))
            }
        }
    }

    @Test
    fun addProviderUseCase(){
        val useCase = AddProviderUseCase(providerDao, userDao, CheckEmployeeLevel)
        val request = AddProviderRequest("name", "address", "description", "phone")
        runBlocking {
            assert(useCase.getResponse(request, token).provider.id != null)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(request)
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(request, token_user)
            }
        }
    }

    @Test
    fun addProviderToProductUseCase(){
        val useCase = AddProviderToProductUseCase(providerDao)

        runBlocking {
            assert(useCase.execute(1L, 1L).id != null)
        }
    }

    @Test
    fun deleteProviderUseCase(){
        val useCase = DeleteProviderUseCase(providerDao, userDao, CheckEmployeeLevel, DeleteProviderToProductByProviderIdUseCase(providerDao))

        runBlocking {
            val provider = providerDao.getAllProvider().first()
            assert(useCase.getResponse(DeleteProviderRequest(provider.id), token).success)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(DeleteProviderRequest(1L), token_user)
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(DeleteProviderRequest(1L))
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(DeleteProviderRequest(Long.MIN_VALUE), token)
            }
        }
    }

    @Test
    fun deleteProviderToProductUseCase(){
        val useCase = DeleteProviderToProductUseCase(providerDao)

        runBlocking {
            val providerToProduct = providerDao.getAllProviderToProduct().first()
            assert(useCase.execute(providerToProduct.id))
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE)
            }
        }
    }

    @Test
    fun updateProviderUseCase(){
        val useCase = UpdateProviderUseCase(providerDao, userDao, CheckEmployeeLevel)
        val name = "test provider name"

        runBlocking {
            val provider = providerDao.getAllProvider().first()
            assert(useCase.getResponse(UpdateProviderRequest(provider.id, name = name), token).provider.name == name)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(UpdateProviderRequest(Long.MIN_VALUE))
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(UpdateProviderRequest(Long.MIN_VALUE), token_user)
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(UpdateProviderRequest(Long.MIN_VALUE, name), token)
            }
        }
    }

    @Test
    fun getProviderUseCase(){
        val useCase = GetProviderUseCase(providerDao, userDao, CheckEmployeeLevel)

        runBlocking {
            val provider = providerDao.getAllProvider().first()
            assert(useCase.getResponse(IdRequest(provider.id), token).provider.id == provider.id)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(IdRequest(Long.MIN_VALUE), token_user)
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(IdRequest(Long.MIN_VALUE))
            }
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.getResponse(IdRequest(Long.MIN_VALUE), token)
            }
        }
    }

    @Test
    fun getAllProviderUseCase(){
        val useCase = GetAllProviderUseCase(providerDao, userDao, CheckEmployeeLevel)

        runBlocking {
            assert(useCase.getResponse(EmptyRequest, token).providers.isNotEmpty())
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
    fun getAllProviderToProductUseCase(){
        val useCase = GetAllProviderToProductUseCase(providerDao)

        runBlocking {
            assert(useCase.execute().isNotEmpty())
        }
    }

    @Test
    fun getAllProviderToProductByProviderIdUseCase(){
        val useCase = GetAllProviderToProductByProviderIdUseCase(providerDao)

        runBlocking {
            val provider = providerDao.getAllProvider().first()
            assert(useCase.execute(provider.id).isNotEmpty())
        }
    }

    @Test
    fun getAllProviderToProductByProductIdUseCase(){
        val useCase = GetAllProviderToProductByProductIdUseCase(providerDao)

        runBlocking {
            assert(useCase.execute(1L).isNotEmpty())
        }
    }
}