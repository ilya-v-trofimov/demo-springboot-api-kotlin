package com.codeit.regoApi.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "vehicle")
data class Vehicle(
    @Id
    val rego: String,
    val make: String?,
    val model: String?,
    @ManyToOne
    var owner: Person? = null,
)
