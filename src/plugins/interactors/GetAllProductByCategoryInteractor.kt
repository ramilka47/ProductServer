package com.flower.server.plugins.interactors

import com.flower.server.database.models.CategoryOfProduct
import com.flower.server.database.models.Product
import com.flower.server.helper.daoCategoryGetter
import com.flower.server.helper.daoCategoryOfProductsGetter
import com.flower.server.helper.daoProductsGetter
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.IdRequest
import com.flower.server.models.response.ProductByCategoryResponse
import com.flower.server.models.result.ProductResponse
import com.flower.server.models.result.toCategoryResponse
import com.flower.server.models.result.toProductResponse

object GetAllProductByCategoryInteractor : Interactor<IdRequest, ProductByCategoryResponse> {

    override suspend fun getResponse(request: IdRequest): ProductByCategoryResponse {
        val category : com.flower.server.database.models.Category =
            daoCategoryGetter
                .category(request.id.toLong())
                ?: throw ExistException("category is not exist")

        val productIdsOfCategory : List<CategoryOfProduct> = daoCategoryOfProductsGetter.allProductOfCategoryId(request.id.toLong())
        val productIds : List<Long> = productIdsOfCategory.map { it.productId }
        val products : List<Product> = daoProductsGetter.productByIds(productIds)

        val result : List<ProductResponse> = products.map { it.toProductResponse(category.toCategoryResponse()) }

        return ProductByCategoryResponse(result)
    }

}