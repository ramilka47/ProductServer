package com.flower.server.core.level_checker

import com.flower.server.helper.BAD_LEVEL
import com.flower.server.helper.execeptions.RequestException

interface ILevelCheckCompositor {

    fun check(level : Int) : Boolean
}

suspend fun <T> ILevelCheckCompositor.check(level : Int, block : suspend ()->T) : T {
    if (check(level)){
        return block()
    } else {
        throw RequestException(BAD_LEVEL)
    }
}