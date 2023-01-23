package com.flower.server.plugins.interactors

import com.flower.server.helper.*
import com.flower.server.helper.execeptions.ExistException
import com.flower.server.models.request.AddProductRequest
import com.flower.server.models.response.AddProductResponse
import com.flower.server.models.result.toCategoryResponse
import com.flower.server.models.result.toProductResponse
import java.io.BufferedOutputStream
import java.io.File

object PostAddProductInteractor : Interactor<AddProductRequest, AddProductResponse> {

    override suspend fun getResponse(request: AddProductRequest): AddProductResponse {
        val existProduct = daoProductsSetter.addProduct(
            name = request.name,
            description = request.description,
            photo =  "",
            count = request.count,
            price = request.price,
            code = request.code) ?: throw ExistException("add product exception")

        val id = existProduct.id.toLong()

        request.photo?.let {bytes->
            val createPhoto = File(photo(id))

            if (createPhoto.exists()){
                createPhoto.delete()
            }

            createPhoto.mkdir()

            BufferedOutputStream(createPhoto.outputStream()).use {
                it.write(bytes)
                it.flush()
            }
        }

        val photoUrl = if (request.photo != null) photoUrl(id.toString()) else ""
        daoProductsSetter.updateProduct(
            id = id,
            name = existProduct.name,
            description = existProduct.description,
            photo = photoUrl,
            count = existProduct.count,
            price = existProduct.price,
            code = existProduct.code
        )

        request.categoryId?.let {categoryId ->
            daoCategoryOfProductsSetter.addCategoryOfProducts(id, categoryId)
        }

        val category = daoCategoryGetter.category(id)

        return AddProductResponse(existProduct.toProductResponse(category?.toCategoryResponse()))
    }

}