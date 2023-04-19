package com.flower.server.repository.provider

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.ProviderDao
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.IdRequest
import com.flower.server.web.models.response.provider.GetProviderResponse
import com.flower.server.web.models.response.provider.toProvider

class GetProviderUseCase(private val providerDao : ProviderDao,
                         private val userDao: UserDao,
                         private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<IdRequest, GetProviderResponse> {

    override suspend fun getResponse(request: IdRequest, token: String?): GetProviderResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level){
            GetProviderResponse(
                provider = providerDao.getProvider(request.id)?.toProvider()
                    ?: throw RequestException("bad provider id=${request.id}")
            )
        }
}