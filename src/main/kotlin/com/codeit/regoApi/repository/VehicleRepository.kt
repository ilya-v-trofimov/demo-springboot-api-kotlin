package com.codeit.regoApi.repository

import com.codeit.regoApi.model.Vehicle
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : CrudRepository<Vehicle, String>
