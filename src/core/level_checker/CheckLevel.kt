package com.flower.server.core.level_checker

class CheckLevel(private val iLevelCheckCompositor: ILevelCheckCompositor) : ILevelCheckCompositor{

    override fun check(level: Int): Boolean =
        iLevelCheckCompositor.check(level)
}