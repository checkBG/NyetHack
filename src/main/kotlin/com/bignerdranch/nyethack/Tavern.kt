package com.bignerdranch.nyethack

import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val menuItems = mutableListOf<String>()

private val menuItemPrice: MutableMap<String, Double> = mutableMapOf()
private val menuItemType: MutableMap<String, String> = mutableMapOf()

private val firstNames = listOf(
    setOf("Alex", "Mordoc", "Sophie", "Tariq"),
    setOf("Amber", "Bella", "Ella", "Connor"),
    setOf("Eva", "Hollie", "Violet", "Jacob"),
    setOf("Jude", "Robbert", "Tommy", "Tyler")
).random()
private val lastNames = listOf(
    setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider"),
    setOf("Adams", "Patterson", "Hill", "Riley"),
    setOf("Begum", "James", "Robinson", "Shaw"),
    setOf("Smith", "Lopez", "Collins", "Kelly"),
    setOf("Davis", "Miller", "Torres", "Evans")
).random()

class Tavern : Room(TAVERN_NAME) {

    init {
        File("data/tavern-menu-data.txt").readText().trimMargin().split("\n").map { it.split(",") }
            .forEach { (type, name, price) ->
                menuItems.add(name)
                menuItemPrice += name to price.toDouble()
                menuItemType += name to type
            }
    }

    private val patrons: MutableSet<String> =
        firstNames.shuffled().zip(lastNames.shuffled()) { firstName, lastName -> "$firstName $lastName" }.toMutableSet()

    private val patronGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        player.name to 4.50,
        *patrons.map { it to 6.00 }.toTypedArray()
    )

    private val itemOfDay =
        patrons.flatMap { getFavoriteMenuItems(it) }.groupingBy { it }.eachCount().maxBy { it.value }.key

    override var status = "Busy"

    override val lootBox: LootBox<Key> = LootBox(Key("key to Nogartse's evil lair"))

    override fun enterRoom() {
        narrate("${player.name} enters $TAVERN_NAME")
        narrate("There are several items fot sale:")
        narrate(menuItems.joinToString())
        narrate("The item of the day is the $itemOfDay")


        val greeting = patrons.firstOrNull()?.let {
            "$it walks over to ${player.name} and says, \"Hi! I'm $it. Welcome to Kronstadt!\""
        } ?: "Nobody greets ${player.name} because the tavern is empty"
        println(greeting)

        val tavernPlaylist = mutableListOf("Korobeiniki", "Kalinka", "Katyusha")
        val nowPlayingMessage = tavernPlaylist.run {
            shuffle()
            "${first()} is currently playing in the tavern"
        }
        println(nowPlayingMessage)

        narrate("${player.name} sees several patrons in the tavern:")
        narrate(patrons.joinToString())

        placeOrder(patrons.random(), menuItems.random())
    }

    private fun placeOrder(
        patronName: String,
        menuItemName: String
    ) {
        val itemPrice = menuItemPrice.getValue(menuItemName)

        narrate("$patronName speaks with $TAVERN_MASTER to place an order")
        if (itemPrice <= patronGold.getOrDefault(patronName, 0.0)) {
            val action = when (menuItemType[menuItemName]) {
                "shandy", "elixir" -> "pours"
                "meal" -> "serves"
                else -> "hands"
            }
            narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
            narrate("$patronName pays $TAVERN_MASTER $itemPrice gold")

            patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
            patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
        } else {
            narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
        }
    }

    fun printMenu() {
        val greeting = ("*** Welcome to $TAVERN_NAME ***").apply { narrate(this) }

        menuItemType.toList().groupBy { it.second }.forEach { (key, value) ->
            println("~[$key]~".middle(greeting.length))

            value.forEach { (item, _) ->
                println(
                    item.indentChar(
                        lastArgument = "${
                            menuItemPrice.getOrDefault(item, 0.0)
                        }", padding = greeting.length
                    )
                )
            }
        }
    }
}

private fun getFavoriteMenuItems(patron: String): List<String> {
    return when (patron) {
        "Alex Ironfoot" -> menuItems.filter { menuItem ->
            menuItemType[menuItem]?.contains("dessert") == true
        }

        else -> menuItems.shuffled().take(Random.nextInt(1..2))
    }
}

fun flipValues(patronToBalance: MutableMap<String, Double>): MutableMap<Double, String> {
    val balanceToPatron = mutableMapOf<Double, String>()
    patronToBalance.forEach { (patron, balance) ->
        balanceToPatron += balance to patron
    }
    return balanceToPatron
}
