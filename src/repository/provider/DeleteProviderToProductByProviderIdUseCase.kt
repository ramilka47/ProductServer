package com.flower.server.repository.provider

import com.flower.server.database.dao.ProviderToProductDao
import java.rmi.ServerException

class DeleteProviderToProductByProviderIdUseCase(private val providerToProductDao : ProviderToProductDao) {

    suspend fun execute(providerId : Long) =
        providerToProductDao.deleteProviderToProductByProviderId(providerId)
}