package com.flower.server.repository.catalog

import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.RelationshipProductToGenreDao
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.catalog.GetAllProductsByGenreResponse
import com.flower.server.web.models.response.catalog.toProduct

class GetAllProductsByGenreUseCase(private val productDao: ProductDao,
                                   private val relationshipProductToGenreDao: RelationshipProductToGenreDao) : UseCase<IdRequest, GetAllProductsByGenreResponse>{

    override suspend fun getResponse(request: IdRequest, token: String?): GetAllProductsByGenreResponse =
        GetAllProductsByGenreResponse(
            productDao
                .getAllProductByIds(
                    relationshipProductToGenreDao
                        .getAllRelationshipByGenre(request.id)
                        .map { it.productId })
                .map { it.toProduct() })

}