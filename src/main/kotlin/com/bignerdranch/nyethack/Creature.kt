package com.bignerdranch.nyethack

import kotlin.math.roundToInt
import kotlin.random.Random

interface Fightable {
    val name: String
    var healthPoints: Int
    var speed: Int

    val diceCount: Int
    val diceSides: Int

    fun takeDamage(damage: Int)

    fun attack(opponent: Fightable) {
        var damageRoll = (0 until diceCount).sumOf {
            Random.nextInt(diceSides + 1)
        }

        if (opponent is Player && player.armorPoints > 0) {
            damageRoll = (damageRoll * ((100 - player.armorPoints) / 100.0)).roundToInt()
        }

        narrate("$name deals $damageRoll to ${opponent.name}")
        opponent.takeDamage(damageRoll)
    }
}

abstract class Monster(
    override val name: String,
    val description: String,
    override var healthPoints: Int,
    override var speed: Int
) : Fightable {
    override fun takeDamage(damage: Int) {
        healthPoints -= damage
    }
}

class Goblin(
    name: String = "Goblin",
    description: String = "A nasty-looking goblin",
    healthPoints: Int = 30,
    speed: Int = 100
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 2
    override val diceSides = 8
}

class Draugr(
    name: String = "Draugr",
    description: String = "The vile Risen Dead - Draugr",
    healthPoints: Int = 50,
    speed: Int = 80
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 3
    override val diceSides = 7
}

class Werewolf(
    name: String = "Werewolf",
    description: String = "A ferocious Werewolf",
    healthPoints: Int = 40,
    speed: Int = 110
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 5
    override val diceSides = 6
}

class Dragon(
    name: String = "Dragon",
    description: String = "The greatest Dragon of the NyetHack",
    healthPoints: Int = 300,
    speed: Int = 30
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 9
    override val diceSides = 5
}

class GrizzlyBear(
    name: String = "Grizzly Bear",
    description: String = "The Grizzly With Huge Claws",
    healthPoints: Int = 70,
    speed: Int = 20
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 2
    override val diceSides = 18
}

class Spider(
    name: String = "Spider",
    description: String = "The Vile Eight-Eyed Spider",
    healthPoints: Int = 35,
    speed: Int = 90
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 3
    override val diceSides = 5
}

class Elf(
    name: String = "Elf",
    description: String = "Handsome Elf",
    healthPoints: Int = 45,
    speed: Int = 100
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 10
    override val diceSides = 3
}

class Skeleton(
    name: String = "Skeleton",
    description: String = "The Bone Skeleton",
    healthPoints: Int = 25,
    speed: Int = 85
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 3
    override val diceSides = 25
}

class Golem(
    name: String = "Golem",
    description: String = "The Impenetrable Golem",
    healthPoints: Int = 100,
    speed: Int = 10
) : Monster(name, description, healthPoints, speed) {
    override val diceCount = 8
    override val diceSides = 11
}