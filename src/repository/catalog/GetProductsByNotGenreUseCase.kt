package com.flower.server.repository.catalog

import com.flower.server.database.dao.*
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.catalog.GetProductsByNotGenreResponse
import com.flower.server.web.models.response.catalog.toProduct

class GetProductsByNotGenreUseCase(private val productDao: ProductDao,
                                   private val relationshipProductToGenreDao: RelationshipProductToGenreDao
) : UseCase<IdRequest, GetProductsByNotGenreResponse> {

    override suspend fun getResponse(
        request: IdRequest,
        token: String?
    ): GetProductsByNotGenreResponse {
        val relationships = relationshipProductToGenreDao.getAllRelationshipByGenre(request.id).map { it.productId }
        return GetProductsByNotGenreResponse(productDao.getAllProduct().filter { it.id !in relationships }.map { it.toProduct() })
    }
}