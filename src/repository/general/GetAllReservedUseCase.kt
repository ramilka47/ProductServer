package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.database.dao.CustomerDao
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.crm.GetAllRelationshipCustomerToOperationUseCase
import com.flower.server.repository.stockroom.GetAllOperationsUseCase

class GetAllReservedUseCase(
    private val productDao: ProductDao,
    private val customerDao: CustomerDao,
    private val getAllRelationshipCustomerToOperationUseCase : GetAllRelationshipCustomerToOperationUseCase,
    private val getAllOperationsUseCase: GetAllOperationsUseCase,
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor) {

}