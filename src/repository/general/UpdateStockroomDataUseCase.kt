package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.StorageProductDataDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.general.GetProductStockroomUseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.request.stockroom.UpdateStockroomDataRequest
import com.flower.server.web.models.response.stockroom.UpdateStockroomDataResponse
import java.rmi.ServerException

class UpdateStockroomDataUseCase(
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
    private val getProductStockroomUseCase: GetProductStockroomUseCase,
    private val storageProductDataDao: StorageProductDataDao) : UseCase<UpdateStockroomDataRequest, UpdateStockroomDataResponse> {

    override suspend fun getResponse(request: UpdateStockroomDataRequest, token: String?): UpdateStockroomDataResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level) {
            if (!storageProductDataDao.updateStorageProductData(
                    request.productId,
                    request.price,
                    request.salePrice,
                    request.discount,
                    request.unicode
                ))
                throw ServerException("can't update product data")

            UpdateStockroomDataResponse(getProductStockroomUseCase.getResponse(IdRequest(request.productId)).product)
        }
}