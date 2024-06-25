package com.bignerdranch.nyethack

abstract class PlayerClassWeapon(
    name: String,
    description: String,
    attackSpeed: Int,
    theAmountOfDamageDone: Int,
    weight: Int
) : Weapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    abstract val availableClass: PlayerClass
}

class Gun(
    name: String = "Gun",
    description: String = "Сan only have a \"Commoner\" class player",
    attackSpeed: Int = 1,
    theAmountOfDamageDone: Int = 50,
    weight: Int = 5
) : PlayerClassWeapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    override val availableClass: PlayerClass by lazy { Commoner() }
}

class MachineGun(
    name: String = "Machine Gun",
    description: String = "Сan only have a \"Commoner\" class player",
    attackSpeed: Int = 5,
    theAmountOfDamageDone: Int = 16,
    weight: Int = 6
) : PlayerClassWeapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    override val availableClass: PlayerClass by lazy { Commoner() }
}

class Bow(
    name: String = "Bow",
    description: String = "Сan only have an \"Archer\" class player",
    attackSpeed: Int = 1,
    theAmountOfDamageDone: Int = 50,
    weight: Int = 5
) : PlayerClassWeapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    override val availableClass: PlayerClass by lazy { Archer() }
}

class Axe(
    name: String = "Axe",
    description: String = "Сan only have a \"Warrior\" class player",
    attackSpeed: Int = 1,
    theAmountOfDamageDone: Int = 60,
    weight: Int = 25
) : PlayerClassWeapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    override val availableClass: PlayerClass by lazy { Warrior() }
}

class HeaviestSword(
    name: String = "Heaviest Sword",
    description: String = "Сan only have a \"Warrior\" class player",
    attackSpeed: Int = 1,
    theAmountOfDamageDone: Int = 100,
    weight: Int = 40
) : PlayerClassWeapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    override val availableClass: PlayerClass by lazy { Warrior() }
}

class RetributionPaladins(
    name: String = "Retribution Paladins",
    description: String = "Can only have an \"Paladin\" class player",
    attackSpeed: Int = 2,
    theAmountOfDamageDone: Int = 35,
    weight: Int = 15
) : PlayerClassWeapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    override val availableClass: PlayerClass by lazy { Paladin() }
}

class Katana(
    name: String = "Katana",
    description: String = "Can only have an \"Ninja\" class player",
    attackSpeed: Int = 5,
    theAmountOfDamageDone: Int = 20,
    weight: Int = 5
) : PlayerClassWeapon(name, description, attackSpeed, theAmountOfDamageDone, weight) {
    override val availableClass: PlayerClass by lazy { Ninja() }
}