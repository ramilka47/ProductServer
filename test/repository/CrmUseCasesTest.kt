package com.flower.server.repository

import com.flower.server.core.LoginPasswordCheckChars
import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.impl.AdminDaoImpl
import com.flower.server.database.dao.impl.CrmDaoImpl
import com.flower.server.helper.DEVELOPER_LEVEL
import com.flower.server.repository.crm.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.rmi.ServerException

class CrmUseCasesTest {

    companion object{
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val NAME = "name"
    }

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val token = generator.generateToken()

    private val crmDao = CrmDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val user = userDao.addUser(NAME, LOGIN, PASSWORD, DEVELOPER_LEVEL, token)
            val customer = crmDao.addCustomer(user!!.login)
            assert(user != null)
            assert(customer != null)
            assert(crmDao.addCustomerToOperation(customer!!.id, 1) != null)
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
    fun addCustomerUseCase(){
        val useCase = AddCustomerUseCase(crmDao)

        runBlocking {
            val user = userDao.getAllUsers().first()
            val customer = useCase.execute(user.login)
            assert(customer.name == user.login)
        }
    }

    @Test
    fun updateCustomerUseCase(){
        val useCase = UpdateCustomerUseCase(crmDao)

        runBlocking {
            val customers = crmDao.getAllCustomer().first()
            assert(useCase.execute(id = customers.id, phone = "new phone"))
        }

        assertThrows(ServerException::class.java){
            runBlocking {
                useCase.execute(id = Long.MIN_VALUE, phone = "new value 2")
            }
        }
    }

    @Test
    fun getCustomerByIdUseCase(){
        val useCase = GetCustomerByIdUseCase(crmDao)

        runBlocking {
            val customer = crmDao.getAllCustomer().first()
            assert(useCase.execute(customer.id).id == customer.id)
        }

        assertThrows(ServerException::class.java){
            runBlocking {
                assert(useCase.execute(Long.MIN_VALUE).name != null)
            }
        }
    }

    @Test
    fun getCustomerByNameUseCase(){
        val useCase = GetCustomerByNameUseCase(crmDao)

        runBlocking {
            val customers = crmDao.getAllCustomer().first()
            assert(useCase.execute(customers.name).id == customers.id)
        }

        assertThrows(ServerException::class.java){
            runBlocking {
                useCase.execute("not available name")
            }
        }
    }

    @Test
    fun getAllRelationshipCustomerToOperationUseCase(){
        val useCase = GetAllRelationshipCustomerToOperationUseCase(crmDao)

        runBlocking {
            val customers = crmDao.getAllCustomer().first()
            assert(useCase.execute().isNotEmpty())
        }
    }

    @Test
    fun getAllRelationshipForCustomerUseCase(){
        val useCase = GetAllRelationshipForCustomerUseCase(crmDao)

        runBlocking {
            val customers = crmDao.getAllCustomer().first()
            assert(useCase.execute(customers.id).isNotEmpty())
        }
    }
}