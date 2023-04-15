package com.flower.server.database

class EnumException(name : String, enumName : String) : Exception("this $name not existing into enum $enumName")