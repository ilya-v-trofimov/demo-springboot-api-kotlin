package com.codeit.regoApi.model

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity(name = "person")
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["first_name", "last_name"])
    ]
)
data class Person(
    @Id
    @GeneratedValue
    @Column(name = "id")
    val id: UUID? = null,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    @Column(name = "address")
    val address: String?
)
