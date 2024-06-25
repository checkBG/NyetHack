package com.bignerdranch.nyethack

abstract class Weapon(
    override val name: String,
    override val description: String,
    val attackSpeed: Int,
    val theAmountOfDamageDone: Int,
    override val weight: Int
) : Wearing {
    override fun descriptionOfTheReservation() {
        narrate("The $name - $description: weapon attack speed - $attackSpeed, the amount of damage done - $theAmountOfDamageDone")
    }
}

class WerewolfClaws(
    name: String = "Werewolf Claws",
    description: String = "Be careful, they cut everything they see",
    attackSpeed: Int = 10,
    theAmountOfDamageDone: Int = 4,
    weight: Int = 0
) : Weapon(name, description, attackSpeed, theAmountOfDamageDone, weight)

class Mace(
    name: String = "Mace",
    description: String = "Heroes fought with her, become the same",
    attackSpeed: Int = 3,
    theAmountOfDamageDone: Int = 10,
    weight: Int = 10
) : Weapon(name, description, attackSpeed, theAmountOfDamageDone, weight)

class IronSword(
    name: String = "Iron Sword",
    description: String = "The simplest and very good at what he does, does not have a high attack speed, but causes relatively good damage",
    attackSpeed: Int = 2,
    theAmountOfDamageDone: Int = 20,
    weight: Int = 15
) : Weapon(name, description, attackSpeed, theAmountOfDamageDone, weight)

class Hammer(
    name: String = "Hammer",
    description: String = "the weapon of a real fighter",
    attackSpeed: Int = 1,
    theAmountOfDamageDone: Int = 60,
    weight: Int = 20
) : Weapon(name, description, attackSpeed, theAmountOfDamageDone, weight)

class Rapier(
    name: String = "Rapier",
    description: String = "Stab a opponent",
    attackSpeed: Int = 5,
    theAmountOfDamageDone: Int = 15,
    weight: Int = 4
) : Weapon(name, description, attackSpeed, theAmountOfDamageDone, weight)