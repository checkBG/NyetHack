@file:Suppress("LeakingThis")

package com.bignerdranch.nyethack

import kotlin.system.exitProcess

// extension
private val String.numVowels
    get() = count { it.lowercase() in "aeiou" }
// extension

open class Player(
    initialName: String,
    val hometown: String = "Saint-Petersburg",
    val isImmortal: Boolean
) : Fightable {
    override var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        set(value) {
            field = value.trim()
        }


    val playerClass: PlayerClass = enterRace()
//        get() = field

    private val maxHealthPoints: Int = playerClass.healthPoints
    override var healthPoints: Int = 0

    val title: String
        get() = when {
            name.all { it.isDigit() } -> "The Identifiable"
            name.none { it.isLetter() } -> "The Witness Protection Member"
            name.lowercase() == name.lowercase().reversed() -> "The Palindrome Carrier"
            name.all { it.isUpperCase() } -> "The Outstanding"
            name.length > 8 -> "The Lengthy"
            name.numVowels > 4 -> "The Master of Vowels"
            else -> "The Renowned Hero"
        }

    private val prophecy by lazy {
        narrate("$name embarks on an arduous quest to locate a fortune teller")
        Thread.sleep(3000)

        narrate("The fortune teller bestows a prophecy upon $name")
        "An intrepid hero from $hometown shall some day " + listOf(
            "form an unlikely bond between two warring factions",
            "take possession of an otherworldly blade",
            "bring the gift of creation back to the world",
            "best the world-eater"
        ).random()
    }

    //    var currentWeapon: Weapon? = WerewolfClaws()
    private var currentArmor: Armor? = null
    private var currentWeapon: Weapon? = null

    override var diceCount = currentWeapon?.attackSpeed ?: 3
    override var diceSides = currentWeapon?.theAmountOfDamageDone ?: 4

    override var speed: Int = 0
    var armorPoints = currentArmor?.theNumberOfArmorPoints ?: 5

    val inventory: MutableList<Any> = mutableListOf()

    var gold = 0

    init {
        healthPoints = maxHealthPoints
        speed = playerClass.speed
        inventory.addAll(playerClass.classInventory)
        inventory.addAll(listOf("stone", "stick"))

        require(name.isNotBlank()) { "Player must have a name" }
    }

    constructor(name: String, hometown: String) : this(
        initialName = name,
        hometown = hometown,
        isImmortal = false
    ) {
        if (name.equals("Jason", ignoreCase = true)) {
            healthPoints = 500
        }
    }

    companion object {
        fun inventory(inventory: MutableList<Any> = player.inventory): String {
            return inventory.groupingBy { it }.eachCount().map { (item, quantity) ->
                try {
                    (item as ItemClass).name
                } catch (e: Exception) {
                    "$item x$quantity"
                }
            }.joinToString("\n")
        }

        fun promptHeroName(): String {
            narrate("A hero enters the town of Kronstadt. What is their name?") //{ message ->
//        "\u001b[33;1m$message\u001b[0m"
//        "\u001b[32;1m$message\u001B[0m"
//    }
            print("Enter a name of the hero: ")
            val input: String? = readlnOrNull()
            require(!input.isNullOrEmpty()) {
                "The hero must have a name"
            }
            return input
//    println("\u001B[32m" + "Madrigal" + "\u001B[0m") // prints this message in Green
//
//    return "Madrigal"
        }

    }

    fun castFireball(numFireballs: Int = 2) {
        narrate("A glass of Fireball springs into existence (x$numFireballs)")
    }

    fun changeName(newName: String) {
        narrate("$name legally changes their name to $newName")
        name = newName
    }

    fun prophesize() {
        narrate("$name thinks about their future")
        narrate("A fortune teller told Madrigal, \"$prophecy\"")
    }

    override fun takeDamage(damage: Int) {
        if (!isImmortal) {
            healthPoints -= damage
        }
    }

    fun info() {
        narrate("The hero has $healthPoints health points")
        narrate("Current weapon - ${currentWeapon?.name ?: "None"}, current armor - ${currentArmor?.name ?: "None"}")
        currentWeapon?.descriptionOfTheReservation() ?: "You haven't current weapon now"
        currentArmor?.descriptionOfTheReservation() ?: "You haven't current armor now"
    }

    private fun addingFeaturesWeapon(weapon: Weapon) {
        diceCount = weapon.attackSpeed
        diceSides = weapon.theAmountOfDamageDone
        speed += (currentWeapon?.weight ?: 0) - weapon.weight
    }

    private fun addingFeaturesArmor(armor: Armor) {
        armorPoints = armor.theNumberOfArmorPoints
        speed += (currentArmor?.weight ?: 0) - armor.weight
    }

    fun changeArmor() {
        val choice = inventory.filterIsInstance<Armor>()
        if (choice.isEmpty()) {
            narrate("You haven't an armor")
        } else {
            narrate(choice.mapIndexed { index, armor ->
                "$index - ${armor.name}"
            }.joinToString("\n"))

            print("Enter the number of the armor you want to put on the character: ")

            val input = readlnOrNull()?.toInt() ?: -1

            if (input !in choice.indices) {
                narrate("Enter the correct number")
            } else {

                val newArmor = choice[input]

                if (currentArmor != null) {
                    inventory.add(currentArmor as Armor)
                }

                narrate("You have changed your armor from ${currentArmor?.name ?: "None"} to ${newArmor.name}")

                inventory.remove(newArmor)
                addingFeaturesArmor(newArmor)
                currentArmor = newArmor
            }
        }
    }

    fun changeWeapon() {
        val choice = inventory.filterIsInstance<Weapon>()
        if (choice.isEmpty()) {
            narrate("You haven't a weapon")
        } else {
            narrate(choice.mapIndexed { index, weapon ->
                "$index - ${weapon.name}"
            }.joinToString("\n"))

            print("Enter the number of the weapon you want to put on the character: ")

            val input = readlnOrNull()?.toInt() ?: -1

            if (input !in choice.indices) {
                narrate("Enter the correct number")
            } else {

                val newWeapon = choice[input]

                if (newWeapon is PlayerClassWeapon) {
                    if (newWeapon.availableClass != playerClass) {
                        narrate("You need to be a class ${newWeapon.availableClass} to use ${newWeapon.name}")
                        return
                    }
                }

                if (currentWeapon != null) {
                    inventory.add(currentWeapon as Weapon)
                }

                narrate("You have changed your weapon from ${currentWeapon?.name ?: "None"} to ${newWeapon.name}")

                inventory.remove(newWeapon)
                addingFeaturesWeapon(newWeapon)
                currentWeapon = newWeapon

            }
        }
    }

    private fun enterRace(
        listOfPlayerClass: List<PlayerClass> = listOf(
            Commoner(), Archer(), Warrior(), Paladin(), Ninja()
        )
    ): PlayerClass {
        listOfPlayerClass.forEachIndexed { index, playerClass ->
            narrate(
                "${index + 1} - ${
                    if (playerClass.name.first().lowercase() in "aeiou") {
                        "an"
                    } else {
                        "a"
                    }
                } ${playerClass.name}: race inventory includes \"${
                    inventory((playerClass).classInventory.toMutableList()).replace("\n", ", ")
                }\", health points are ${playerClass.healthPoints}, speed is ${playerClass.speed}"
            )
        }

        print("Choose a number of race from the above: ")

        val input: Int = try {
            readln().toInt()
        } catch (e: Exception) {
            -1
        }

        require(input in 1..listOfPlayerClass.size) {
            "Choose number from 1 to ${listOfPlayerClass.size}"
        }

        return listOfPlayerClass[input - 1]
    }

    fun harakiri() {
        if (playerClass is Ninja) {
            narrate("You are committing harakiri, farewell...")
            exitProcess(0)
        } else {
            narrate("You can't do that, because you're not a Ninja class")
        }
    }
}
