package com.flower.server.repository

import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.core.level_checker.CheckAdminLevel
import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.impl.AdminDaoImpl
import com.flower.server.database.dao.impl.CatalogDaoImpl
import com.flower.server.database.dao.impl.StockroomDaoImpl
import com.flower.server.database.models.storage.StorageOperationEnum
import com.flower.server.helper.DEVELOPER_LEVEL
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.helper.getCurrentTimeInSec
import com.flower.server.repository.catalog.GetProductInfoUseCase
import com.flower.server.repository.general.GetProductStockroomUseCase
import com.flower.server.repository.general.UpdateStockroomDataUseCase
import com.flower.server.repository.stockroom.*
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.stockroom.UpdateStockroomDataRequest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StockroomUseCasesTest {

    companion object{
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val NAME = "name"
        private const val COUNT = 10
    }

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val token = generator.generateToken()

    private val stockroomDao = StockroomDaoImpl()
    private val productDao = CatalogDaoImpl()

    @Before
    fun before(){
        TestDatabaseFactory.init()
        runBlocking {
            val user = userDao.addUser(NAME, LOGIN, PASSWORD, DEVELOPER_LEVEL, token)
            val product = productDao.addProduct(
                name = "name",
                description = "description",
                photo = "photo",
                producer = "producer",
                gallery = listOf()
            )
            assert(user != null)
            assert(product != null)
            assert(stockroomDao.addCount(product!!.id) != null)
            assert(stockroomDao.updateCount(product.id, COUNT))
            assert(stockroomDao.addBuy(product.id, 0, 100.0, getCurrentTimeInSec()) != null)
            assert(stockroomDao.addStorageProductData(product.id, 100.0, 0f, "") != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            userDao.getAllUsers().forEach {
                assert(userDao.deleteUser(it.id))
            }
            productDao.deleteAllProduct()
            stockroomDao.getAllOperation().forEach {
                stockroomDao.deleteOperation(it.id)
            }
        }
    }

    @Test
    fun addOperationBuyUseCase(){
        val useCase = AddOperationBuyUseCase(productDao, stockroomDao, UpdateProductCountUseCase(stockroomDao), stockroomDao)
        val count = 5
        runBlocking {
            val products = productDao.getAllProduct().first()
            assert(useCase.execute(products.id, count, 100.0, getCurrentTimeInSec()).count == count)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE, count, 100.0, getCurrentTimeInSec())
            }
        }
    }

    @Test
    fun addOperationReserveUseCase(){
        val useCase = AddOperationReserveUseCase(productDao, stockroomDao, UpdateProductCountUseCase(stockroomDao), stockroomDao)
        val count = 5
        runBlocking {
            val products = productDao.getAllProduct().first()
            assert(useCase.execute(products.id, count, 100.0, getCurrentTimeInSec()).count == count)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE, count, 100.0, getCurrentTimeInSec())
            }
        }
    }

    @Test
    fun addOperationReturnUseCase(){
        val useCase = AddOperationReturnUseCase(productDao, stockroomDao, UpdateProductCountUseCase(stockroomDao), stockroomDao)
        val count = 5
        runBlocking {
            val products = productDao.getAllProduct().first()
            assert(useCase.execute(products.id, count, 100.0, getCurrentTimeInSec()).count ==  count)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE, count, 100.0, getCurrentTimeInSec())
            }
        }
    }

    @Test
    fun addOperationSaleUseCase(){
        val useCase = AddOperationSaleUseCase(productDao, stockroomDao, UpdateProductCountUseCase(stockroomDao), stockroomDao)
        val count = 5
        runBlocking {
            val products = productDao.getAllProduct().first()
            assert(useCase.execute(products.id, count, 100.0, getCurrentTimeInSec()).count == count)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE, count, 100.0, getCurrentTimeInSec())
            }
        }
    }

    @Test
    fun addOperationWriteOffUseCase(){
        val useCase = AddOperationWriteOffUseCase(productDao, stockroomDao, UpdateProductCountUseCase(stockroomDao), stockroomDao)
        val count = 5
        runBlocking {
            val products = productDao.getAllProduct().first()
            assert(useCase.execute(products.id, count, 100.0, getCurrentTimeInSec()).count == count)
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                useCase.execute(Long.MIN_VALUE, count, 100.0, getCurrentTimeInSec())
            }
        }
    }

    @Test
    fun deleteOperationUseCase(){
        val useCase = DeleteOperationUseCase(stockroomDao)
        runBlocking {
            val operation = stockroomDao.getAllOperation().first()
            assert(useCase.execute(operation.id))
        }

        Assert.assertThrows(RequestException::class.java){
            runBlocking {
                assert(useCase.execute(Long.MIN_VALUE))
            }
        }
    }

    @Test
    fun getAllBoughtUseCase(){
        val useCase = GetAllBoughtUseCase(stockroomDao)
        runBlocking {
            assert(useCase.execute().isNotEmpty())
        }
    }

    @Test
    fun getAllOperationByIdsUseCase(){
        val useCase = GetAllOperationByIdsUseCase(stockroomDao)
        runBlocking {
            val operation = stockroomDao.getAllOperation()
            assert(useCase.execute(operation.map { it.id }).isNotEmpty())
        }
    }

    @Test
    fun getAllOperationsUseCase(){
        val useCase = GetAllOperationsUseCase(stockroomDao)
        runBlocking {
            assert(useCase.execute().isNotEmpty())
        }
    }

    @Test
    fun getAllReservedUseCase(){
        val useCase = GetAllReservedUseCase(stockroomDao)
        runBlocking {
            assert(useCase.execute().isEmpty())
        }
    }

    @Test
    fun getAllReturnedUseCase(){
        val useCase = GetAllReturnedUseCase(stockroomDao)
        runBlocking {
            assert(useCase.execute().isEmpty())
        }
    }

    @Test
    fun getAllSalesUseCase(){
        val useCase = GetAllSalesUseCase(stockroomDao)
        runBlocking {
            assert(useCase.execute().isEmpty())
        }
    }

    @Test
    fun getAllWriteOffsUseCase(){
        val useCase = GetAllWriteOffsUseCase(stockroomDao)
        runBlocking {
            assert(useCase.execute().isEmpty())
        }
    }

    @Test
    fun getProductCountUseCase(){
        val useCase = GetProductCountUseCase(stockroomDao)
        runBlocking {
            val product = productDao.getAllProduct().first()
            assert(useCase.getResponse(IdRequest(product.id), token).productCount.productId == product.id)
        }
    }

    @Test
    fun updateOperationUseCase(){
        val useCase = UpdateOperationUseCase(stockroomDao)
        runBlocking {
            val operation = stockroomDao.getAllOperation().first()
            assert(useCase.execute(operation.id, StorageOperationEnum.BUY).id == operation.id)
        }
    }

    @Test
    fun updateStockroomDataUseCase(){
        val useCase = UpdateStockroomDataUseCase(
            userDao,
            CheckAdminLevel,
            GetProductStockroomUseCase(
                GetProductInfoUseCase(
                    productDao,
                    productDao,
                    productDao,
                    productDao,
                    productDao),
                stockroomDao,
                stockroomDao),
            stockroomDao)

        runBlocking {
            val product = productDao.getAllProduct().first()
            assert(useCase.getResponse(UpdateStockroomDataRequest(productId = product.id, discount = 4.0f), token).product.id == product.id)
        }
    }


}