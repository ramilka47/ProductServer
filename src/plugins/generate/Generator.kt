package com.flower.server.plugins.generate

class Generator : IGenerator {
    override fun generateToken(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..128)
            .map { allowedChars.random() }
            .joinToString("")
    }
}