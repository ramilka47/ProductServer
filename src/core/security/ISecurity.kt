package com.flower.server.core.security

interface ISecurity {

    fun secure(token : String) : Boolean

}