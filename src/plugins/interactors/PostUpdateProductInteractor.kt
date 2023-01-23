package com.flower.server.plugins.interactors

import com.flower.server.database.models.CategoryOfProduct
import com.flower.server.helper.*
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.helper.execeptions.InternalException
import com.flower.server.models.request.UpdateProductRequest
import com.flower.server.models.response.UpdateProductResult
import java.io.BufferedOutputStream
import java.io.File

object PostUpdateProductInteractor : Interactor<UpdateProductRequest, UpdateProductResult> {

    override suspend fun getResponse(request: UpdateProductRequest): UpdateProductResult {
        val id = request.id ?: throw InternalException("id is not edded")
        val trueProduct = daoProductsGetter.product(id) ?: throw ExistException("product is not exists")
        daoProductsSetter.updateProduct(
            id,
            request.name?:trueProduct.name,
            request.description?:trueProduct.description,
            trueProduct.photo,
            request.count?:trueProduct.count,
            request.price?:trueProduct.price,
            request.code?:trueProduct.code)

        request.photo?.let {bytes->
            val photoFile = File(photo(id))
            photoFile.delete()
            photoFile.mkdir()
            BufferedOutputStream(photoFile.outputStream()).use {
                it.write(bytes)
                it.flush()
            }
        }

        request.categoryId?.let {categoryId ->
            val categoryOfProduct : CategoryOfProduct? = daoCategoryOfProductsGetter.categoryOfProducts(id)
            if (categoryOfProduct == null) {
                daoCategoryOfProductsSetter.addCategoryOfProducts(id, categoryId)
                daoCategoryOfProductsGetter.categoryOfProducts(id)
            }
            categoryOfProduct?.let {
                if (it.categoryId != categoryId)
                    daoCategoryOfProductsSetter.updateCategoryOfProducts(id, categoryId)
            }
        }

        return UpdateProductResult()
    }

}