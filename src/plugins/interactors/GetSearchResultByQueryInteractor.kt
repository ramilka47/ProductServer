package com.flower.server.plugins.interactors

import com.flower.server.helper.daoProductsGetter
import com.flower.server.models.request.SearchQueryRequest
import com.flower.server.models.response.SearchResultResponse

object GetSearchResultByQueryInteractor : Interactor<SearchQueryRequest, SearchResultResponse> {

    override suspend fun getResponse(request: SearchQueryRequest): SearchResultResponse {
        return SearchResultResponse(daoProductsGetter.allProductsByName(request.query))
    }

}