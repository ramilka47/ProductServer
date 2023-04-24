package com.flower.server.repository.general

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.database.dao.ProductDao
import com.flower.server.repository.stockroom.AddOperationReturnUseCase

class ReturnProductUseCase(
    private val productDao: ProductDao,
    private val iLevelCheckCompositor: ILevelCheckCompositor,
    private val addOperationReturnUseCase: AddOperationReturnUseCase) {
}