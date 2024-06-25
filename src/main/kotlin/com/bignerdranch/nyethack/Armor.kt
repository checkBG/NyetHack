package com.bignerdranch.nyethack

abstract class Armor(
    override val name: String,
    override val description: String,
    val theNumberOfArmorPoints: Int,
    override val weight: Int
) : Wearing {
    override fun descriptionOfTheReservation() {
        narrate("The $name - $description: armor weight - $weight, the number of armor points - $theNumberOfArmorPoints")
    }
}

class LeatherArmor(
    name: String = "Leather Armor",
    description: String = "Animal skin armor, light and at the same time gives too little armor",
    theNumberOfArmorPoints: Int = 10,
    armorWeight: Int = 5
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)

class IronArmor(
    name: String = "Iron Armor",
    description: String = "Is heavy, but it gives a lot of armor",
    theNumberOfArmorPoints: Int = 30,
    armorWeight: Int = 20
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)

class ObsidianArmor(
    name: String = "Obsidian Armor",
    description: String = "Armor from the very depths of the world, it is not advised to wear weaklings, a lot of armor means a lot of weight",
    theNumberOfArmorPoints: Int = 50,
    armorWeight: Int = 50
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)


class AshenArmor(
    name: String = "Ashen Armor",
    description: String = "The armor is straight from the volcano, medium in severity and does not give too much armor",
    theNumberOfArmorPoints: Int = 30,
    armorWeight: Int = 10
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)

class StoneArmor(
    name: String = "Stone Armor",
    description: String = "",
    theNumberOfArmorPoints: Int = 30,
    armorWeight: Int = 40
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)

class ArmorMadeOfShell(
    name: String = "Armor Made Of Shell",
    description: String = "It's hard to craft, but what's the use, a lot of armor, average speed reduction",
    theNumberOfArmorPoints: Int = 40,
    armorWeight: Int = 25
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)

class DemonArmor(
    name: String = "Demon Armor",
    description: String = "By making a deal with the devil, you have gained a power the like of which does not exist",
    theNumberOfArmorPoints: Int = 85,
    armorWeight: Int = 1
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)

class DivineArmor(
    name: String = "Divine Armor",
    description: String = "It is one of the best",
    theNumberOfArmorPoints: Int = 80,
    armorWeight: Int = 1
) : Armor(name, description, theNumberOfArmorPoints, armorWeight)
