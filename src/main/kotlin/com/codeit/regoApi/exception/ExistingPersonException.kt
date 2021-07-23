package com.codeit.regoApi.exception

data class ExistingPersonException(private val msg: String) : RuntimeException(msg)
