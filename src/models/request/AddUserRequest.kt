package com.flower.server.models.request

import com.flower.server.models.IRequest

data class AddUserRequest(val name : String,
                          val login : String,
                          val password : String,
                          val level : Int) : IRequest