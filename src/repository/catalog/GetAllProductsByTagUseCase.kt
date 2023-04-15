package com.flower.server.repository.catalog

import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.RelationshipProductToTagDao
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.catalog.GetAllProductsByTagResponse
import com.flower.server.web.models.response.catalog.toProduct

class GetAllProductsByTagUseCase(private val productDao: ProductDao,
                                 private val relationshipProductToTagDao: RelationshipProductToTagDao) : UseCase<IdRequest, GetAllProductsByTagResponse>{

    override suspend fun getResponse(request: IdRequest, token: String?): GetAllProductsByTagResponse =
        GetAllProductsByTagResponse(
            productDao
                .getAllProductByIds(
                    relationshipProductToTagDao
                        .getAllRelationshipByTag(request.id)
                        .map { it.productId })
                .map { it.toProduct() })
}