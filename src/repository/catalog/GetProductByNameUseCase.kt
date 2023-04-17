package com.flower.server.repository.catalog

import com.flower.server.database.dao.*
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.catalog.GetProductByNameRequest
import com.flower.server.web.models.response.catalog.GetProductByNameResponse
import com.flower.server.web.models.response.catalog.toProduct

class GetProductByNameUseCase(private val productDao: ProductDao
) : UseCase<GetProductByNameRequest, GetProductByNameResponse>{

    override suspend fun getResponse(request: GetProductByNameRequest, token: String?): GetProductByNameResponse =
        GetProductByNameResponse(productDao.getProducts(request.string).map { it.toProduct() })

}