package com.flower.server.core.level_checker

import com.flower.server.helper.MODER_LEVEL

object CheckModerLevel : ILevelCheckCompositor {

    override fun check(level: Int): Boolean =
        level >= MODER_LEVEL
}