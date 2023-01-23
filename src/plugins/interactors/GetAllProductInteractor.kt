package com.flower.server.plugins.interactors

import com.flower.server.helper.daoProductsGetter
import com.flower.server.models.request.EmptyRequest
import com.flower.server.models.response.AllProductResponse

object GetAllProductInteractor : Interactor<EmptyRequest, AllProductResponse> {

    override suspend fun getResponse(request: EmptyRequest): AllProductResponse {
        return AllProductResponse(daoProductsGetter.allProducts())
    }

}