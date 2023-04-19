package com.flower.server.repository

import com.flower.server.core.LoginPasswordCheckChars
import com.flower.server.core.generate.Generator
import com.flower.server.core.generate.IGenerator
import com.flower.server.database.dao.impl.AdminDaoImpl

class ProviderUseCasesTest {

    private val userDao = AdminDaoImpl()
    private val generator : IGenerator = Generator()
    private val token = generator.generateToken()
    private val loginPasswordCheckChars = LoginPasswordCheckChars()
}