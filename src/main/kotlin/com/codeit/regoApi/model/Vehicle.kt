package com.codeit.regoApi.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "vehicle")
data class Vehicle(
    @Id
    val rego: String,
    val make: String?,
    val model: String?,
)
