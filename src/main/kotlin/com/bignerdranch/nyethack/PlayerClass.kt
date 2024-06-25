package com.bignerdranch.nyethack

abstract class PlayerClass(
    val name: String,
    var speed: Int,
    var healthPoints: Int,
    val classInventory: List<Any>
)

class Commoner(
    initialName: String = "Commoner",
    classSpeed: Int = 100,
    classHealthPoints: Int = 100,
    classInventory: List<Any> = listOf(Gemstones(0), "iron-ingot", Gun())
) : PlayerClass(initialName,classSpeed, classHealthPoints, classInventory)

class Archer(
    initialName: String = "Archer",
    classSpeed: Int = 80,
    classHealthPoints: Int = 130,
    classInventory: List<Any> = listOf(Gemstones(0)) + List<Any>(100) { "Arrow" } + listOf(Bow())
) : PlayerClass(initialName, classSpeed, classHealthPoints, classInventory)

class Warrior(
    initialName: String = "Warrior",
    classSpeed: Int = 60,
    classHealthPoints: Int = 200,
    classInventory: List<Any> = listOf(Gemstones(0), IronSword(), IronArmor(), HeaviestSword())
) : PlayerClass(initialName, classSpeed, classHealthPoints, classInventory)

class Paladin(
    initialName: String = "Paladin",
    classSpeed: Int = 90,
    classHealthPoints: Int = 140,
    classInventory: List<Any> = listOf(Gemstones(0), RetributionPaladins())
) : PlayerClass(initialName, classSpeed, classHealthPoints, classInventory)

class Ninja(
    initialName: String = "Ninja",
    classSpeed: Int = 150,
    classHealthPoints: Int = 75,
    classInventory: List<Any> = listOf(Gemstones(0), Katana())
) : PlayerClass(initialName, classSpeed, classHealthPoints, classInventory)