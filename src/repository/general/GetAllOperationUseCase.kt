package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.database.dao.ProductDao
import com.flower.server.database.dao.RelationshipCustomerToOperationDao
import com.flower.server.database.dao.UserDao
import com.flower.server.repository.stockroom.GetAllOperationByIdsUseCase

class GetAllOperationUseCase(
    private val productDao: ProductDao,
    private val relationshipCustomerToOperationDao: RelationshipCustomerToOperationDao,
    private val getAllOperationByIdsUseCase: GetAllOperationByIdsUseCase,
    private val userDao: UserDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor) {
}