package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.*
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.catalog.AddTagsIntoProductRequest
import com.flower.server.web.models.response.catalog.AddTagsIntoProductResponse
import java.lang.Exception

class AddTagsIntoProductUseCase(private val productDao: ProductDao,
                                private val relationshipProductToTagDao: RelationshipProductToTagDao,
                                private val userDao: UserDao,
                                private val tagsDao: TagDao,
                                private val iLevelCheckCompositor: ILevelCheckCompositor,
                                private val getProductInfoUseCase: GetProductInfoUseCase
) : UseCase<AddTagsIntoProductRequest, AddTagsIntoProductResponse> {

    override suspend fun getResponse(request: AddTagsIntoProductRequest, token: String?): AddTagsIntoProductResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level) {
            if (request.tagIds.isEmpty() || tagsDao.getAllTagsByIds(request.tagIds).size != request.tagIds.size)
                throw RequestException("not tag with this ids")
            if (productDao.getProduct(request.productId) == null)
                throw RequestException("not product with this id")
            request.tagIds.forEach {
                relationshipProductToTagDao.addTagInProduct(it, request.productId)
            }

            AddTagsIntoProductResponse(getProductInfoUseCase.getResponse(IdRequest(request.productId)).product)
        }
}