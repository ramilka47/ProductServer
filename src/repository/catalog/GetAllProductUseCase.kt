package com.flower.server.repository.catalog

import com.flower.server.database.dao.*
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.catalog.GetAllProductResponse
import com.flower.server.web.models.response.catalog.toProduct

class GetAllProductUseCase(private val productDao: ProductDao
) : UseCase<EmptyRequest, GetAllProductResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllProductResponse =
        GetAllProductResponse(products = productDao.getAllProduct().map { it.toProduct() })
}