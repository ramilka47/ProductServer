package com.flower.server.plugins.interactors

import com.flower.server.database.models.Product
import com.flower.server.helper.Constants
import com.flower.server.helper.daoCategoryGetter
import com.flower.server.helper.daoCategoryOfProductsGetter
import com.flower.server.helper.daoProductsGetter
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.models.request.PageRequest
import com.flower.server.models.result.*

object PostProductPageInteractor : Interactor<PageRequest, PageResponse<ProductResponse>> {

    override suspend fun getResponse(request: PageRequest): PageResponse<ProductResponse> {
        if (request.page <= 0) {
            throw RequestException()
        }

        val result : List<Product> = daoProductsGetter.productsByPageSort(sort = request.sort, offset = request.page)
        val categoryOfProducts = daoCategoryOfProductsGetter.allProductOfCategoryProductIds(result.map { it.id.toLong() })
        val categories = daoCategoryGetter.allProductOfCategoryIds(categoryOfProducts.map { it.categoryId }.distinct())
        val size = daoProductsGetter.productsSize()

        return PageResponse(request.page, (size / Constants.PAGE_SIZE).toInt().dec(),
            result.map {product->
                product.toProductResponse(categories.find { category ->
                    categoryOfProducts.find {
                        it.productId == product.id.toLong()
                    }?.categoryId == category.id
                }?.toCategoryResponse())
            })
    }

}