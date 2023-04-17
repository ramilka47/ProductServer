package com.flower.server.repository

import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.core.level_checker.CheckAdminLevel
import com.flower.server.database.TestDatabaseFactory
import com.flower.server.database.dao.IDaoCatalog
import com.flower.server.database.dao.impl.AdminDaoImpl
import com.flower.server.database.dao.impl.CatalogDaoImpl
import com.flower.server.database.dao.impl.StockroomDaoImpl
import com.flower.server.helper.DEVELOPER_LEVEL
import com.flower.server.helper.USER_LEVEL
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.catalog.*
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.catalog.*
import com.flower.server.web.models.response.catalog.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import java.rmi.ServerException

class CatalogUseCasesTest {

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val stockroomDao = StockroomDaoImpl()
    private val token = generator.generateToken()
    private val token_user = generator.generateToken()
    private val daoCatalog : IDaoCatalog = CatalogDaoImpl()

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
            assert(userDao.addUser("user name", "user_login", "user_password", USER_LEVEL, token_user) != null)
            assert(daoCatalog.addGenre("genre") != null)
            assert(daoCatalog.addTag("tag") != null)
            assert(daoCatalog.addProduct("name", "description", "photo", "producer", listOf()) != null)
        }
    }

    @After
    fun after(){
        runBlocking {
            userDao.getAllUsers().forEach {
                assert(userDao.deleteUser(it.id))
            }
            daoCatalog.getAllGenres().forEach {
                assert(daoCatalog.deleteGenre(it.id))
            }
            daoCatalog.getAllTags().forEach {
                assert(daoCatalog.deleteTag(it.id))
            }
            daoCatalog.getAllProduct().forEach {
                assert(daoCatalog.deleteProduct(it.id))
            }
        }
    }

    @Test
    fun createTag(){
        val useCase = CreateTagUseCase(daoCatalog, userDao, CheckAdminLevel)
        runBlocking {
            assert(useCase.getResponse(CreateTagRequest("new tag"), token).name == "new tag")
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(CreateTagRequest("new tag"), token_user)
                }
            }
        }
    }

    @Test
    fun createGenre(){
        val useCase = CreateGenreUseCase(daoCatalog, userDao, CheckAdminLevel)
        runBlocking {
            assert(useCase.getResponse(CreateGenreRequest("new genre"), token).name == "new genre")
        }
        assertThrows(RequestException::class.java){
            runBlocking {
                assert(useCase.getResponse(CreateGenreRequest("new genre"), token_user).name == "new genre")
            }
        }
    }

    @Test
    fun createProduct(){
        val useCase = CreateProductUseCase(daoCatalog, userDao, CheckAdminLevel, stockroomDao, stockroomDao)
        runBlocking {
            assert(useCase.getResponse(CreateProductRequest(
                "new product name",
                "new product description",
                "new product photo",
                "new product producer",
                listOf()
            ), token).product.name == "new product name")
        }
        assertThrows(RequestException::class.java){
            runBlocking {
                assert(useCase.getResponse(CreateProductRequest(
                    "new product name",
                    "new product description",
                    "new product photo",
                    "new product producer",
                    listOf()
                ), token_user).product.name == "new product name")
            }
        }
    }

    @Test
    fun updateTag(){
        runBlocking {
            val tag = daoCatalog.getAllTags().first()
            val useCase = UpdateTagUseCase(daoCatalog, userDao, CheckAdminLevel)
            val newNameTag = "new tag name"
            val func : suspend (String)->UpdateTagNameResponse = {
                useCase.getResponse(UpdateTagNameRequest(tag.id, newNameTag), it)
            }
            assert(func(token).name == newNameTag)

            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
        }
    }

    @Test
    fun updateGenre(){
        runBlocking {
            val genre = daoCatalog.getAllGenres().first()
            val useCase = UpdateGenreNameUseCase(daoCatalog, userDao, CheckAdminLevel)
            val newNameGenre = "new genre name"
            val func : suspend (String)->UpdateGenreNameResponse = {
                useCase.getResponse(UpdateGenreNameRequest(genre.id, newNameGenre), it)
            }
            assert(func(token).name == newNameGenre)

            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
        }
    }

    @Test
    fun updateProduct(){
        runBlocking {
            val product = daoCatalog.getAllProduct().first()
            val useCase = UpdateProductUseCase(daoCatalog, userDao, CheckAdminLevel)
            val newNameProduct = "new product name"
            val func : suspend (String)->UpdateProductResponse = {
                useCase.getResponse(UpdateProductRequest(id = product.id, name = newNameProduct, gallery = listOf("gallery")), it)
            }
            assert(func(token).product.gallery.isNotEmpty())

            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
        }
    }

    @Test
    fun deleteTag(){
        runBlocking {
            val tag = daoCatalog.getAllTags().first()
            val useCase = DeleteTagUseCase(daoCatalog, userDao, CheckAdminLevel)
            val func : suspend (String)->DeleteTagResponse = {
                useCase.getResponse(DeleteTagRequest(tag.id), it)
            }

            assert(func(token).success)
            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
            assertThrows(BadUserTokenException::class.java){
                runBlocking {
                    useCase.getResponse(DeleteTagRequest(Long.MIN_VALUE))
                }
            }
            assertThrows(ServerException::class.java){
                runBlocking {
                    useCase.getResponse(DeleteTagRequest(Long.MIN_VALUE), token)
                }
            }
        }
    }

    @Test
    fun deleteGenre(){
        runBlocking {
            val genre = daoCatalog.getAllGenres().first()
            val useCase = DeleteGenreUseCase(daoCatalog, userDao, CheckAdminLevel)
            val func : suspend (String)->DeleteGenreResponse = {
                useCase.getResponse(DeleteGenreRequest(genre.id), it)
            }

            assert(func(token).success)
            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
            assertThrows(BadUserTokenException::class.java){
                runBlocking {
                    useCase.getResponse(DeleteGenreRequest(Long.MIN_VALUE))
                }
            }
            assertThrows(ServerException::class.java){
                runBlocking {
                    useCase.getResponse(DeleteGenreRequest(Long.MIN_VALUE), token)
                }
            }
        }
    }

    @Test
    fun deleteProduct(){
        runBlocking {
            val product = daoCatalog.getAllProduct().first()
            val useCase = DeleteProductUseCase(daoCatalog, userDao, CheckAdminLevel)
            val func : suspend (String)->DeleteProductResponse = {
                useCase.getResponse(IdRequest(product.id), it)
            }

            assert(func(token) != null)
            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
            assertThrows(BadUserTokenException::class.java){
                runBlocking {
                    useCase.getResponse(IdRequest(Long.MIN_VALUE))
                }
            }
            assertThrows(ServerException::class.java){
                runBlocking {
                    useCase.getResponse(IdRequest(Long.MIN_VALUE), token)
                }
            }
        }
    }

    @Test
    fun addGenresIntoProduct(){
        runBlocking {
            val genres = daoCatalog.getAllGenres()
            val product = daoCatalog.getAllProduct().first()
            val useCase = AddGenresIntoProductUseCase(
                daoCatalog,
                daoCatalog,
                userDao,
                daoCatalog,
                CheckAdminLevel,
                GetProductInfoUseCase(daoCatalog, daoCatalog, daoCatalog, daoCatalog, daoCatalog)
            )
            val func : suspend (String) -> AddGenresIntoProductResponse = {
                useCase.getResponse(AddGenresIntoProductRequest(product.id, genres.map { it.id }), it)
            }

            assert(func(token).product.genres.isNotEmpty())
            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddGenresIntoProductRequest(product.id, listOf()), token)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddGenresIntoProductRequest(Long.MIN_VALUE, genres.map { it.id }), token)
                }
            }
        }
    }

    @Test
    fun addTagsIntoProduct(){
        runBlocking {
            val tags = daoCatalog.getAllTags()
            val product = daoCatalog.getAllProduct().first()
            val useCase = AddTagsIntoProductUseCase(
                daoCatalog,
                daoCatalog,
                userDao,
                daoCatalog,
                CheckAdminLevel,
                GetProductInfoUseCase(daoCatalog, daoCatalog, daoCatalog, daoCatalog, daoCatalog)
            )
            val func : suspend (String) -> AddTagsIntoProductResponse = {
                useCase.getResponse(AddTagsIntoProductRequest(product.id, tags.map { it.id }), it)
            }

            assert(func(token).product.tags.isNotEmpty())
            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddTagsIntoProductRequest(product.id, listOf()), token)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddTagsIntoProductRequest(Long.MIN_VALUE, tags.map { it.id }), token)
                }
            }
        }
    }

    @Test
    fun addProductsIntoGenre(){
        runBlocking {
            val products = daoCatalog.getAllProduct()
            val genre = daoCatalog.getAllGenres().first()
            val useCase = AddProductsIntoGenreUseCase(
                daoCatalog,
                daoCatalog,
                daoCatalog,
                userDao,
                CheckAdminLevel
            )
            val func : suspend (String) -> AddProductsIntoGenreResponse = {
                useCase.getResponse(AddProductsIntoGenreRequest(genre.id, products.map { it.id }), it)
            }

            assert(func(token).products.size == products.size)
            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddProductsIntoGenreRequest(genre.id, listOf()), token)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddProductsIntoGenreRequest(Long.MIN_VALUE, products.map { it.id }), token)
                }
            }
        }
    }

    @Test
    fun addProductsIntoTag(){
        runBlocking {
            val products = daoCatalog.getAllProduct()
            val tag = daoCatalog.getAllTags().first()
            val useCase = AddProductsIntoTagUseCase(
                daoCatalog,
                daoCatalog,
                daoCatalog,
                userDao,
                CheckAdminLevel
            )
            val func : suspend (String) -> AddProductsIntoTagResponse = {
                useCase.getResponse(AddProductsIntoTagRequest(tag.id, products.map { it.id }), it)
            }

            assert(func(token).products.size == products.size)
            assertThrows(RequestException::class.java){
                runBlocking {
                    func(token_user)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddProductsIntoTagRequest(tag.id, listOf()), token)
                }
            }
            assertThrows(RequestException::class.java){
                runBlocking {
                    useCase.getResponse(AddProductsIntoTagRequest(Long.MIN_VALUE, products.map { it.id }), token)
                }
            }
        }
    }

    @Test
    fun getAllProductsByGenre(){
        addProductsIntoGenre()
        runBlocking {
            val genre = daoCatalog.getAllGenres().first()
            val useCase = GetAllProductsByGenreUseCase(daoCatalog, daoCatalog)
            assert(useCase.getResponse(IdRequest(genre.id)).products.isNotEmpty())
        }
    }

    @Test
    fun getAllProductByTag(){
        addProductsIntoTag()
        runBlocking {
            val tag = daoCatalog.getAllTags().first()
            val useCase = GetAllProductsByTagUseCase(daoCatalog, daoCatalog)
            assert(useCase.getResponse(IdRequest(tag.id)).products.isNotEmpty())
        }
    }

    @Test
    fun getProductsByNotGenre(){
        runBlocking {
            val genre = daoCatalog.getAllGenres().first()
            val useCase = GetProductsByNotGenreUseCase(daoCatalog, daoCatalog)
            assert(useCase.getResponse(IdRequest(genre.id)).products.isNotEmpty())
        }
    }

    @Test
    fun getProductByNotTag(){
        runBlocking {
            val tag = daoCatalog.getAllTags().first()
            val useCase = GetProductsByNotTagUseCase(daoCatalog, daoCatalog)
            assert(useCase.getResponse(IdRequest(tag.id)).products.isNotEmpty())
        }
    }

    @Test
    fun getProductByName(){
        runBlocking {
            val product = daoCatalog.getAllProduct().first()
            val useCase = GetProductByNameUseCase(daoCatalog)
            assert(useCase.getResponse(GetProductByNameRequest(product.name)).products.isNotEmpty())
        }
    }

}