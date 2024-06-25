package com.bignerdranch.nyethack

import kotlin.random.Random
import kotlin.random.nextInt

open class Room(val name: String) {

    protected open var status = "Calm"

    open val lootBox: LootBox<Loot> = LootBox.random()

    open fun description(): String {
        return "$name (Currently: $status)"
    }

    open fun enterRoom() {
        narrate("There is nothing to do here")
    }
}

open class MonsterRoom(
    name: String,
    var monster: Monster? = when (Random.nextInt(1..600)) {
        in 1..100 -> Goblin()
        in 101..150 -> Draugr()
        in 151..180 -> Werewolf()
        in 181..220 -> GrizzlyBear()
        in 221..350 -> Spider()
        in 351..420 -> Elf()
        in 421..520 -> Skeleton()
        else -> Golem()
    }
) : Room(name) {
    override fun description() =
        "$name (Currently: ${if (monster == null) super.status else "Dangerous"}) (Creature: ${monster?.description ?: "None"})"

    override fun enterRoom() {
        if (monster == null) {
            super.enterRoom()
        } else {
            narrate("Danger is lurking in this room")
        }
    }
}

open class TownSquare : Room("The Town Square") {

    override var status = "Bustling"
    private val bellSound = "GWONG"

    private val hatDropOffBox = DropOffBox<Hat>()
    private val gemDropOffBox = DropOffBox<Gemstones>()

    final override fun enterRoom() {
        narrate("The villagers rally and cheer as the hero enters")
        ringBell()
    }

    fun ringBell() {
        narrate("The bell tower announces the hero's presence: $bellSound")
    }

    fun <T> sellBoot(
        loot: T
    ): Int where T : Loot, T : Sellable {
        return when (loot) {
            is Hat -> hatDropOffBox.sellLoot(loot)
            is Gemstones -> gemDropOffBox.sellLoot(loot)
            else -> throw Exception("The item being sold is not gems or hats")
        }
    }
}
