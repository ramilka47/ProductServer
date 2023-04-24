package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.database.dao.ProductDao

class GetAllOperationForUserUseCase(
    private val productDao: ProductDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
) {
}