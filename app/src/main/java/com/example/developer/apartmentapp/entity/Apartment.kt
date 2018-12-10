package com.example.developer.apartmentapp.entity

import java.io.Serializable

data class Apartment(val id: Int, var numberOfBeds: Int, var bookedInterval: LongRange? = null) : Serializable