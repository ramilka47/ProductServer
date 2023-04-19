package com.flower.server.repository.provider

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.ProviderDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.provider.AddProviderRequest
import com.flower.server.web.models.response.provider.AddProviderResponse
import com.flower.server.web.models.response.provider.toProvider
import java.rmi.ServerException

class AddProviderUseCase(private val providerDao : ProviderDao,
                         private val userDao: UserDao,
                         private val iLevelCheckCompositor: ILevelCheckCompositor) : UseCase<AddProviderRequest, AddProviderResponse> {

    override suspend fun getResponse(request: AddProviderRequest, token: String?): AddProviderResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level) {
            AddProviderResponse(
                providerDao.addProvider(
                    name = request.name,
                    address = request.address,
                    description = request.description,
                    phone = request.phone
                )?.toProvider() ?: throw ServerException("can't add provider")
            )
        }
}