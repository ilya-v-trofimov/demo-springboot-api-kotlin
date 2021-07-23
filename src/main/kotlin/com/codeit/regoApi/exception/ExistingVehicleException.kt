package com.codeit.regoApi.exception

data class ExistingVehicleException(private val msg: String) : RuntimeException(msg)
