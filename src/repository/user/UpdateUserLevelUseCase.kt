package com.flower.server.repository.user

import com.flower.server.core.level_checker.ILevelCheckCompositor
import com.flower.server.core.level_checker.check
import com.flower.server.database.dao.UserDao
import com.flower.server.helper.BAD_LEVEL
import com.flower.server.helper.execeptions.BadUserTokenException
import com.flower.server.helper.execeptions.RequestException
import com.flower.server.repository.UseCase
import com.flower.server.repository.getUser
import com.flower.server.web.models.request.user.UpdateUserLevelRequest
import com.flower.server.web.models.response.user.UpdateUserLevelResponse

class UpdateUserLevelUseCase(private val userDao : UserDao, private val iLevelCheckCompositor : ILevelCheckCompositor) : UseCase<UpdateUserLevelRequest, UpdateUserLevelResponse>{

    override suspend fun getResponse(request: UpdateUserLevelRequest, token: String?): UpdateUserLevelResponse {
        val userEntity = getUser(userDao, token)
        return iLevelCheckCompositor.check(userEntity.level){
            if (userDao.updateUser(id = request.id, level = request.newLevel)){
                val user = userDao.getUser(request.id)!!
                UpdateUserLevelResponse(user.id, user.login, user.level)
            } else {
                throw RequestException("user with this id not found")
            }
        }
    }

}