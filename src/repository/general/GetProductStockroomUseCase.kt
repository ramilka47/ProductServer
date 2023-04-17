package com.flower.server.repository.general

import com.flower.server.database.dao.CountDao
import com.flower.server.database.dao.StorageProductDataDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.catalog.GetProductInfoUseCase
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.stockroom.GetProductByUnicodeResponse
import com.flower.server.web.models.response.stockroom.GetProductResponse
import com.flower.server.web.models.response.stockroom.Product
import java.rmi.ServerException

class GetProductStockroomUseCase(
    private val getProductInfoUseCase: GetProductInfoUseCase,
    private val countDao: CountDao,
    private val storageProductDataDao: StorageProductDataDao
) : UseCase<IdRequest, GetProductResponse> {

    override suspend fun getResponse(request: IdRequest, token: String?): GetProductResponse {
        val product = getProductInfoUseCase.getResponse(request).product
        val count = countDao.getCount(product.id)
            ?: countDao.addCount(product.id)
            ?: throw ServerException("can't get and add count")
        val data = storageProductDataDao.getStorageProductData(product.id)
            ?: throw RequestException("not product with this code")

        return GetProductResponse(
            product = Product(
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
            )
        )
    }
}