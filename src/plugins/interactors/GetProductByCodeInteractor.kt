package com.flower.server.plugins.interactors

import com.flower.server.helper.daoProductsGetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.IdRequest
import com.flower.server.models.result.ProductResponse
import com.flower.server.models.result.toProductResponse

object GetProductByCodeInteractor : Interactor<IdRequest, ProductResponse> {

    override suspend fun getResponse(request: IdRequest): ProductResponse {
        return daoProductsGetter.productByCode(request.id)?.toProductResponse() ?: throw ExistException("product by code not exists")
    }

}