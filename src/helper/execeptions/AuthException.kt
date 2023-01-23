package com.flower.server.helper.execeptions

class AuthException(state : State) : ClientException("auth exception error in ${state.value}")

sealed class State(val value : String){
    object Login : State("login")
    object Password : State("password")
}