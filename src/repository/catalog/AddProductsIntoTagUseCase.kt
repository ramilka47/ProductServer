package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.*
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.AddProductsIntoTagRequest
import com.flower.server.web.models.response.catalog.AddProductsIntoTagResponse
import com.flower.server.web.models.response.catalog.toProduct

class AddProductsIntoTagUseCase(private val productDao: ProductDao,
                                private val tagDao: TagDao,
                                private val relationshipProductToTagDao: RelationshipProductToTagDao,
                                private val userDao: UserDao,
                                private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<AddProductsIntoTagRequest, AddProductsIntoTagResponse> {

    override suspend fun getResponse(request: AddProductsIntoTagRequest, token: String?): AddProductsIntoTagResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level) {
            if (tagDao.getTag(request.tagId) == null)
                throw RequestException("not tag with this id")
            if (request.productIds.isEmpty() || productDao.getAllProductByIds(request.productIds).size != request.productIds.size)
                throw RequestException("not product with this ids")

            request.productIds.forEach {
                relationshipProductToTagDao.addProductInTag(it, request.tagId)
            }

            AddProductsIntoTagResponse(productDao.getAllProductByIds(request.productIds).map { it.toProduct() })
        }
}