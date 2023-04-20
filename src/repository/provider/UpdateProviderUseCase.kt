package com.flower.server.repository.provider

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.ProviderDao
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.provider.UpdateProviderRequest
import com.flower.server.web.models.response.provider.UpdateProviderResponse
import com.flower.server.web.models.response.provider.toProvider
import java.rmi.ServerException

class UpdateProviderUseCase(private val providerDao : ProviderDao,
                            private val userDao: UserDao,
                            private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<UpdateProviderRequest, UpdateProviderResponse> {

    override suspend fun getResponse(request: UpdateProviderRequest, token: String?): UpdateProviderResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level) {
            UpdateProviderResponse(
                if (providerDao.updateProvider(
                        id = request.id,
                        name = request.name,
                        address = request.address,
                        description = request.description,
                        phone = request.phone
                    )
                )
                    providerDao.getProvider(request.id)?.toProvider()
                        ?: throw RequestException("bad provider id=${request.id}")
                else
                    providerDao.getProvider(request.id)?.let {
                        throw ServerException("can't update provider id=${request.id}")
                    }
                        ?: throw RequestException("bad provider id=${request.id}")
            )
        }
}