package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.UpdateProductRequest
import com.flower.server.web.models.response.catalog.toProduct
import com.flower.server.web.models.response.catalog.UpdateProductResponse
import java.rmi.ServerException

class UpdateProductUseCase(private val productDao: ProductDao,
                           private val userDao: UserDao,
                           private val iLevelCheckCompositor: ILevelCheckCompositor) : UseCase<UpdateProductRequest, UpdateProductResponse> {

    override suspend fun getResponse(request: UpdateProductRequest, token: String?): UpdateProductResponse {
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level) {
            if (!productDao.updateProduct(request.id, request.name, request.description, request.photo, request.producer, request.gallery)){
                throw ServerException("can't update product")
            }
            val product = productDao.getProduct(request.id) ?: throw ServerException("can't get product")
            UpdateProductResponse(product = product.toProduct())
        }
    }

}