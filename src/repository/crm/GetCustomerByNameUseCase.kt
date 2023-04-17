package com.flower.server.repository.crm

import com.flower.server.database.dao.CustomerDao
import com.flower.server.web.models.response.crm.Customer
import com.flower.server.web.models.response.crm.toCustomer
import java.rmi.ServerException

class GetCustomerByNameUseCase(private val customerDao : CustomerDao) {

    suspend fun execute(name : String) : Customer =
        customerDao.getCustomer(name)?.toCustomer() ?: throw ServerException("not found customer with name=$name")
}