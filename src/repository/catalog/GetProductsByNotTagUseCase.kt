package com.flower.server.repository.catalog

import com.flower.server.database.dao.*
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.catalog.GetProductsByNotTagResponse
import com.flower.server.web.models.response.catalog.toProduct

class GetProductsByNotTagUseCase(private val productDao: ProductDao,
                                 private val relationshipProductToTagDao: RelationshipProductToTagDao) : UseCase<IdRequest, GetProductsByNotTagResponse>{

    override suspend fun getResponse(request: IdRequest, token: String?): GetProductsByNotTagResponse {
        val relationships = relationshipProductToTagDao.getAllRelationshipByTag(request.id).map { it.productId }
        return GetProductsByNotTagResponse(productDao.getAllProduct().filter { it.id !in relationships }.map { it.toProduct() })
    }
}