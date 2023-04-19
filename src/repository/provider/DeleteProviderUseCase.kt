package com.flower.server.repository.provider

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.ProviderDao
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.provider.DeleteProviderRequest
import com.flower.server.web.models.response.provider.DeleteProviderResponse
import java.rmi.ServerException

class DeleteProviderUseCase(private val providerDao : ProviderDao,
                            private val userDao: UserDao,
                            private val iLevelCheckCompositor: ILevelCheckCompositor,
                            private val deleteProviderToProductByProviderIdUseCase : DeleteProviderToProductByProviderIdUseCase
) : UseCase<DeleteProviderRequest, DeleteProviderResponse> {

    override suspend fun getResponse(request: DeleteProviderRequest, token: String?): DeleteProviderResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level){
            DeleteProviderResponse(
                providerDao.deleteProvider(request.id).apply {
                    if (!this) {
                        providerDao.getProvider(request.id)?.let {
                            throw ServerException("can't delete provider id=${request.id}")
                        } ?: throw RequestException("bad provider id=${request.id}")
                    } else {
                        deleteProviderToProductByProviderIdUseCase.execute(request.id)
                    }
                }
            )
        }
}