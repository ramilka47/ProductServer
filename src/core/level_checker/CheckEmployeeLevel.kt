package com.flower.server.core.level_checker

import com.flower.server.helper.EMPLOYEE_LEVEL

object CheckEmployeeLevel : ILevelCheckCompositor {

    override fun check(level: Int): Boolean =
        level >= EMPLOYEE_LEVEL
}