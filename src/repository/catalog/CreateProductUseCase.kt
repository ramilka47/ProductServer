package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.StorageProductDataDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.catalog.CreateProductRequest
import com.flower.server.web.models.response.catalog.AddProduct
import com.flower.server.web.models.response.catalog.toProduct
import com.flower.server.web.models.response.catalog.CreateProductResponse
import java.rmi.ServerException

class CreateProductUseCase(private val productDao: ProductDao,
                           private val userDao: UserDao,
                           private val iLevelCheckCompositor: ILevelCheckCompositor,
                           private val countDao: CountDao,
                           private val storageProductDataDao: StorageProductDataDao
) : UseCase<CreateProductRequest, CreateProductResponse> {

    override suspend fun getResponse(request: CreateProductRequest, token: String?): CreateProductResponse {
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level) {
            val product = productDao.addProduct(
                request.name,
                request.description,
                request.photo,
                request.producer,
                request.gallery)?.toProduct() ?: throw ServerException("can't add product")
            val count = countDao.addCount(productId = product.id)?.count!!
            val data = storageProductDataDao.addStorageProductData(
                productId = product.id,
                price = request.price ?: 0.0,
                discount = request.discount ?: 0f,
                uniCode = request.unicode ?: ""
            )!!
            CreateProductResponse(AddProduct(
                product.id,
                product.name,
                product.description,
                product.photo,
                product.producer,
                product.gallery,
                count,
                data.price,
                data.discount,
                data.uniCode
            ))
        }
    }
}