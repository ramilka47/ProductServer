package com.flower.server.core.security

class ImplSecurity : ISecurity{
    override fun secure(token: String): Boolean = true

}