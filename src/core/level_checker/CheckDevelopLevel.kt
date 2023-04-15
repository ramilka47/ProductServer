package com.flower.server.core.level_checker

import com.flower.server.helper.DEVELOPER_LEVEL

object CheckDevelopLevel : ILevelCheckCompositor {

    override fun check(level: Int): Boolean =
        level >= DEVELOPER_LEVEL
}