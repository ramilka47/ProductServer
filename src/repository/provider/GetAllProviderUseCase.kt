package com.flower.server.repository.provider

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.ProviderDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.provider.GetAllProviderResponse
import com.flower.server.web.models.response.provider.toProvider

class GetAllProviderUseCase(private val providerDao : ProviderDao,
                            private val userDao: UserDao,
                            private val iLevelCheckCompositor: ILevelCheckCompositor
) : UseCase<EmptyRequest, GetAllProviderResponse> {

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllProviderResponse =
        iLevelCheckCompositor.check(getUser(userDao, token).level){
            GetAllProviderResponse(
                providerDao.getAllProvider().map { it.toProvider() }
            )
        }
}