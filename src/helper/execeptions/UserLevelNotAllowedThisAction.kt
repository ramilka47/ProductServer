package com.flower.server.helper.execeptions

class UserLevelNotAllowedThisAction(level : Int, action : String) : ClientException("user level is $level this level is too small for this $action")