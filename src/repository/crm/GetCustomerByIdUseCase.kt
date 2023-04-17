package com.flower.server.repository.crm

import com.flower.server.database.dao.CustomerDao
import com.flower.server.web.models.response.crm.Customer
import com.flower.server.web.models.response.crm.toCustomer
import java.rmi.ServerException

class GetCustomerByIdUseCase(private val customerDao : CustomerDao) {

    suspend fun execute(id : Long) : Customer =
        customerDao.getCustomer(id)?.toCustomer() ?: throw ServerException("not found customer with id=$id")
}