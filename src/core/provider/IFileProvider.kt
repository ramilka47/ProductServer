package com.flower.server.core.provider

interface IFileProvider {

    fun addFile(byteArray: ByteArray, fileName : String): String
}