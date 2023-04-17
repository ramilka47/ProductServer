package com.flower.server.repository.crm

import com.flower.server.database.dao.CustomerDao
import com.flower.server.repository.UseCase
import com.flower.server.web.models.request.EmptyRequest
import com.flower.server.web.models.response.crm.GetAllCustomersResponse
import com.flower.server.web.models.response.crm.toCustomer

class GetAllCustomerUseCase(private val customerDao : CustomerDao) : UseCase<EmptyRequest, GetAllCustomersResponse>{

    override suspend fun getResponse(request: EmptyRequest, token: String?): GetAllCustomersResponse =
        GetAllCustomersResponse(customerDao.getAllCustomer().map { it.toCustomer() })
}