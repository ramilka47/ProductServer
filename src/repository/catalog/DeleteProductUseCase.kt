package com.flower.server.repository.catalog

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.catalog.DeleteProductResponse
import java.rmi.ServerException

class DeleteProductUseCase(private val productDao: ProductDao,
                           private val userDao: UserDao,
                           private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<IdRequest, DeleteProductResponse> {

    override suspend fun getResponse(request: IdRequest, token: String?): DeleteProductResponse {
        val user = getUser(userDao, token)

        return iLevelCheckCompositor.check(user.level) {
            if (!productDao.deleteProduct(request.id)){
                throw ServerException("can't delete product")
            }
            DeleteProductResponse()
        }
    }
}