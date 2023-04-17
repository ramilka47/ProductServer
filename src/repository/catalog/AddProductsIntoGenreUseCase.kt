package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.*
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.AddProductsIntoGenreRequest
import com.flower.server.web.models.response.catalog.AddProductsIntoGenreResponse
import com.flower.server.web.models.response.catalog.toProduct

class AddProductsIntoGenreUseCase(private val productDao: ProductDao,
                                  private val genreDao: GenreDao,
                                  private val relationshipProductToGenreDao: RelationshipProductToGenreDao,
                                  private val userDao: UserDao,
                                  private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<AddProductsIntoGenreRequest, AddProductsIntoGenreResponse> {

    override suspend fun getResponse(
        request: AddProductsIntoGenreRequest,
        token: String?
    ): AddProductsIntoGenreResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level) {
            if (genreDao.getGenre(request.genreId) == null)
                throw RequestException("not genre with this id")
            if (request.productIds.isEmpty() || productDao.getAllProductByIds(request.productIds).size != request.productIds.size)
                throw RequestException("not product with this ids")

            request.productIds.forEach {
                relationshipProductToGenreDao.addProductInGenre(it, request.genreId)
            }

            AddProductsIntoGenreResponse(productDao.getAllProductByIds(request.productIds).map { it.toProduct() })
        }
}