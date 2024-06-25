package com.bignerdranch.nyethack

import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.exitProcess

// extensions
private operator fun MutableList<MutableList<Room>>.get(coordinate: Coordinate) =
    getOrNull(coordinate.y)?.getOrNull(coordinate.x)

private infix fun Coordinate.move(direction: Direction) =
    direction.updateCoordinate(this)

private fun String.addEnthusiasm(enthusiasmLevel: Int = 1) =
    this + "!".repeat(enthusiasmLevel)

private fun Room?.orEmptyRoom(name: String = "the middle of nowhere"): Room =
    this ?: Room(name)
// extensions

object Game {
    private val worldMap = mutableListOf(
        mutableListOf(TownSquare(), Tavern(), Room("Back Room")),
        mutableListOf(MonsterRoom("A Long Corridor"), Room("A Generic Room")),
        mutableListOf<Room>(MonsterRoom("The Dungeon"))
    )

    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)

    init {
//        fun enterRace(
//            listOfPlayerClass: List<Player> = listOf(
//                Commoner(), Archer(), Warrior(), Paladin(), Ninja()
//            )
//        ): Player {
//                listOfPlayerClass.forEachIndexed { index, playerClass ->
//                    narrate(
//                        "${index + 1} - ${
//                            if (playerClass.name.first().lowercase() in "aeiou") {
//                                "an"
//                            } else {
//                                "a"
//                            }
//                        } ${playerClass.name}: race inventory includes \"${
//                            Player.inventory((playerClass).classInventory.toMutableList()).replace("\n", ", ")
//                        }\", health points are ${playerClass.healthPoints}, speed is ${playerClass.speed}"
//                    )
//                }
//
//                print("Choose a number of race from the above: ")
//
//                val input: Int = try {
//                    readln().toInt()
//                } catch (e: Exception) {
//                    -1
//                }
//
//                require(input in 1..listOfPlayerClass.size) {
//                    "Choose number from 1 to ${listOfPlayerClass.size}"
//                }
//
//                return listOfPlayerClass[input - 1]
//        }
//        player.playerClass = enterRace()

        val lengthOfWorld = 20
        val widthOfWorld = 20

        while (worldMap.size < lengthOfWorld) {
            worldMap.add(
                mutableListOf(
                    listOf(
                        MonsterRoom(
                            listOf(
                                "The Catacombs",
                                "The Dungeon",
                                "The Mountain Of Dangers",
                                "The Field Of Battle",
                                "River Bank"
                            ).random()
                        ), Room(
                            listOf(
                                "A Generic Room",
                                "A Long Corridor",
                                "Recreation Room",
                                "The Room Of The Hall"
                            ).random()
                        )
                    ).random()
                )
            )
        }

        worldMap.forEach { room ->
            repeat(Random.nextInt(3..<lengthOfWorld)) {
                room.add(
                    when (Random.nextInt(1..10)) {
                        in 1..4 -> {
                            Room(
                                listOf(
                                    "A Generic Room",
                                    "A Long Corridor",
                                    "Recreation Room",
                                    "The Room Of The Hall"
                                ).random()
                            )
                        }

                        else -> {
                            MonsterRoom(
                                listOf(
                                    "The Catacombs",
                                    "The Dungeon",
                                    "The Mountain Of Dangers",
                                    "The Field Of Battle",
                                    "River Bank"
                                ).random()
                            )
                        }
                    }
                )
            }
        }

        val widthWhenWillBeTavern = Random.nextInt(3..<widthOfWorld)
        val lengthWhenWillBeTavern = worldMap[widthWhenWillBeTavern].size
        val lengthWhenWillBeDragon = worldMap.last().size - 1

        worldMap[widthWhenWillBeTavern][Random.nextInt(0..<lengthWhenWillBeTavern)] = Tavern()

        worldMap.last()[lengthWhenWillBeDragon] = MonsterRoom(name = "The boss's room", monster = Dragon())

        narrate("Welcome, adventurer".frame(5))
        val mortality = if (player.isImmortal) "an immortal" else "a mortal"

        narrate("${player.name} of ${player.hometown}, $mortality, has ${player.healthPoints} health points")
        narrate("The hero is ${player.playerClass}, his inventory includes \n${Player.inventory()}")
    }

    fun play() {
        while (true) {
            narrate("${player.name} - ${player.title}, is in ${currentRoom.description()}")
            currentRoom.enterRoom()
            print("> Enter your command: ")
            GameInput(readlnOrNull()).processCommand()
        }
    }

    private fun move(direction: Direction) {
        val newPosition = currentPosition move direction
        val newRoom = worldMap[newPosition].orEmptyRoom()

        narrate("The hero moves ${direction.name}")
        currentPosition = newPosition
        currentRoom = newRoom
    }

    private fun fight() {
        val monsterRoom = currentRoom as? MonsterRoom
        val currentMonster = monsterRoom?.monster

        if (currentMonster == null) {
            narrate("There's nothing to fight here")
            return
        }

        var combatRound = 0
        val previousNarrationModifier = narrationModifier
        narrationModifier = { it.addEnthusiasm(enthusiasmLevel = combatRound) }

        while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
            combatRound++

            player.attack(currentMonster)

            if (currentMonster.healthPoints > 0) {
                currentMonster.attack(player)
            }

            Thread.sleep(1000)
        }
        narrationModifier = previousNarrationModifier

        if (player.healthPoints <= 0) {
            narrate("You have been defeated! The ${currentMonster.name} has ${currentMonster.healthPoints} health points left... Thanks for playing")
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated")
            monsterRoom.monster = null
        }
    }

    private fun mapOfGame(): String {
        val blueColor = "\u001b[36m"
//        val greenColor = "\u001b[32m"

        val reset = "\u001b[0m" // to reset color to the default

        val mapOfGame = mutableListOf<MutableList<String>>()

        worldMap.indices.forEach { index -> mapOfGame.add(MutableList(worldMap[index].size) { blueColor + "O" + reset }) }

        try {
            mapOfGame[currentPosition.y][currentPosition.x] = "X"
        } catch (e: Exception) {
            return "You are beyond this world. Your coordinate: x = ${currentPosition.x}, y = ${currentPosition.y}"
        }

        return mapOfGame.joinToString("\n") { it.joinToString(" ") }

    }

    private fun takeLoot() {
        if (currentRoom is MonsterRoom) {
            if ((currentRoom as MonsterRoom).monster != null) {
                narrate("First kill the monster, and then get the loot")
                return
            }
        }

        val loot = currentRoom.lootBox.takeLoot()

        player.apply {
            if (loot == null) {
                narrate("$name approaches the loot box, but it's empty")
            } else {
                narrate("$name now has a ${loot.name}")

                if (loot is Gemstones) {
                    inventory[0] = inventory.first() as Gemstones + loot
                } else {
                    inventory += loot
                }
            }
        }
//        if (loot == null) {
//            narrate("${player.name} approaches the loot box, but it's empty")
//        } else {
//            narrate("${player.name} now has a ${loot.name}")
//
//            if (loot is Gemstones) {
//                player.inventory[0] = player.inventory.first() as Gemstones + loot
//            } else {
//                player.inventory += loot
//            }
//        }
    }

    private fun sellLoot(soldItem: String) {
        when (currentRoom) {
            is TownSquare -> {
                when (soldItem) {
                    "hat" -> {
                        val hats =
                            player.inventory.filterIsInstance<Hat>().mapIndexed { index, hat -> index to hat }.toMap()

                        hats.forEach { (index, hat) -> narrate("$index - ${hat.name} cost G${hat.value}") }

                        print("Enter a number from 0 to ${hats.size - 1}: ")

                        val input = try {

                            val sequence = readln().split("-").map { it.toInt() }

                            (sequence.first()..sequence.last())
                        } catch (e: Exception) {
                            narrate("Enter the correct number")
                            return
                        }

                        if (input.first() !in 0..<hats.size || input.last() !in 0..<hats.size) {
                            narrate("Choose number from 0 to ${hats.size - 1}")
                            return
                        }

                        for (hat in input) {
                            val hatForSale = hats[hat] ?: return
                            val sellPrice = (currentRoom as TownSquare).sellBoot(hatForSale)

                            narrate("Sold ${hatForSale.name} for $sellPrice gold")

                            player.gold += sellPrice
                            player.inventory.remove(hatForSale)
                        }
                    }

                    "gemstones" -> {
                        val amountOfGemstones: Int = (player.inventory.first() as Gemstones).value

                        narrate("You have gemstones x$amountOfGemstones")

                        if (amountOfGemstones <= 0) {
                            narrate("you cannot sell $amountOfGemstones gems")
                            return
                        }

                        print("Enter how many gems you want to sell: ")

                        val count = try {
                            readln().toInt()
                        } catch (e: Exception) {
                            narrate("Enter the correct amount")
                            return
                        }

                        if (count > amountOfGemstones) {
                            narrate("You want to sell more than you have")
                            return
                        }

                        val sellPrice = (currentRoom as TownSquare).sellBoot(Gemstones(count))

                        narrate("Sold gemstones x${count} for $sellPrice")

                        player.gold += sellPrice
                        player.inventory[0] = player.inventory.first() as Gemstones - Gemstones(count)
                    }

                    else -> narrate("I don't know what you're trying to sell")
                }
            }

            else -> narrate("You cannot sell anything here")
        }
    }

    private fun showGold() {
        narrate("You have ${player.gold} gold")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() {
            return when (command.lowercase()) {
                "change" -> {
                    when (argument) {
                        "name" -> {
                            print("Enter your new name: ")
                            player.changeName(readlnOrNull() ?: "Madrigal")
                        }

                        "armor" -> player.changeArmor()

                        "weapon" -> player.changeWeapon()

                        else -> narrate("I don't know what you want to change")
                    }
                }

                "inventory" -> narrate("The inventory includes\n${Player.inventory()}")

                "info" -> player.info()

                "fight" -> fight()

                "move" -> {
                    val direction = Direction.entries.firstOrNull { it.name.equals(argument, ignoreCase = true) }
                    if (direction != null) {
                        move(direction)
                    } else {
                        narrate("I don't know what direction that is")
                    }
                }

                "map" -> narrate(mapOfGame())

                "ring" -> {
                    if (currentRoom is TownSquare && argument.startsWith("x")) {
                        narrate("${player.name} is ringing the bells")
                        repeat(argument.drop(1).toInt()) {
                            (currentRoom as TownSquare).ringBell()
                        }
                    } else if (!argument.startsWith("x")) {
                        narrate("The number of times must start with \"x\"")
                    } else {
                        narrate("The hero is not in the Town Square, there are no bells here")
                    }
                }

                "cast" -> {
                    when (argument) {
                        "fireball" -> player.castFireball()
                        else -> narrate("I don't know what spell that is")
                    }
                }

                "prophesize" -> player.prophesize()

                in listOf("quit", "exit") -> {
                    narrate("Our game is over. Have a nice day!")
                    exitProcess(0)
                }

                in listOf("harakiri", "suicide") -> player.harakiri()

                "kill" -> {
                    if (argument.equals("myself", ignoreCase = true)) {
                        player.harakiri()
                    } else {
                        narrate("Who do you wanna kill there? So kill")
                    }
                }

                "take" -> {
                    if (argument.equals("loot", ignoreCase = true)) {
                        takeLoot()
                    } else {
                        narrate("I don't know what you're trying to take")
                    }
                }

                "sell" -> sellLoot(argument.lowercase())

                "show" -> if (argument.equals("gold", ignoreCase = true)) {
                    showGold()
                } else {
                    narrate("What can I show you?")
                }

                else -> narrate("I'm not sure what you're trying to do")
            }
        }
    }
}
