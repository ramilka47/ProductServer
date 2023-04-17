package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.*
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.catalog.AddGenresIntoProductRequest
import com.flower.server.web.models.response.catalog.AddGenresIntoProductResponse

class AddGenresIntoProductUseCase(private val productDao: ProductDao,
                                  private val relationshipProductToGenreDao: RelationshipProductToGenreDao,
                                  private val userDao: UserDao,
                                  private val genreDao: GenreDao,
                                  private val iLevelCheckCompositor: ILevelCheckCompositor,
                                  private val getProductInfoUseCase: GetProductInfoUseCase
) : UseCase<AddGenresIntoProductRequest, AddGenresIntoProductResponse> {

    override suspend fun getResponse(
        request: AddGenresIntoProductRequest,
        token: String?
    ): AddGenresIntoProductResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level) {
            if (request.genreIds.isEmpty() || genreDao.getAllGenresByIds(request.genreIds).size != request.genreIds.size)
                throw RequestException("not genre with this ids")
            if (productDao.getProduct(request.productId) == null)
                throw RequestException("not product with this id")
            request.genreIds.forEach {
                relationshipProductToGenreDao.addGenreInProduct(it, request.productId)
            }

            AddGenresIntoProductResponse(getProductInfoUseCase.getResponse(IdRequest(request.productId)).product)
        }
}