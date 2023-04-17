package com.flower.server.repository.general

import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.StorageProductDataDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.catalog.GetProductInfoUseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.stockroom.GetProductByUnicodeRequest
import com.flower.server.web.models.response.stockroom.GetProductByUnicodeResponse
import com.flower.server.web.models.response.stockroom.Product
import java.rmi.ServerException

class GetProductByUnicodeUseCase(
    private val getProductInfoUseCase: GetProductInfoUseCase,
    private val countDao: CountDao,
    private val storageProductDataDao: StorageProductDataDao
) : UseCase<GetProductByUnicodeRequest, GetProductByUnicodeResponse> {

    override suspend fun getResponse(request: GetProductByUnicodeRequest, token: String?): GetProductByUnicodeResponse {
        val data = storageProductDataDao.getStorageProductData(request.unicode)
            ?: throw RequestException("not product with this code")
        val product = getProductInfoUseCase.getResponse(IdRequest(data.productId)).product
        val count = countDao.getCount(data.productId)
            ?: countDao.addCount(data.productId)
            ?: throw ServerException("can't get and add count")

        return GetProductByUnicodeResponse(product = Product(
            id = product.id,
            product.name,
            product.description,
            product.photo,
            product.producer,
            product.gallery,
            product.genres,
            product.tags,
            count.count,
            data.uniCode,
            data.price,
            data.discount
        ))
    }
}