package com.codeit.regoApi.exception

data class ExistingPersonException(private val msg: String) : RuntimeException(msg)
data class ExistingVehicleException(private val msg: String) : RuntimeException(msg)
data class PersonNotFound(val msg: String) : RuntimeException(msg)
data class VehicleNotFound(val msg: String) : RuntimeException(msg)
data class InvalidPersonDetails(val msg: String) : RuntimeException(msg)
