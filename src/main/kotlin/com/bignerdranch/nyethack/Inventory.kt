package com.bignerdranch.nyethack

interface ItemClass {
    val name: String
}

interface Wearing : ItemClass {
    val description: String
    val weight: Int

    fun descriptionOfTheReservation()
}