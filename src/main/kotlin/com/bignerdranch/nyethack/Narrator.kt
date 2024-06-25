package com.bignerdranch.nyethack

import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it }

inline fun narrate(
    message: String, modifier: (String) -> String = { narrationModifier(it) }
) {
    println(modifier(message).frame(5, "#") + "\n")
}

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String

    when (Random.nextInt(1..8)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }

        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }

        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }

        4 -> {
            var narrationGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationGiven++
                "$message. \n(I have narrated $narrationGiven things)"
            }
        }

        5 -> {
            mood = "lazy"
            modifier = { message ->
                message.take(message.length / 2)
            }
        }

        6 -> {
            mood = "mysterious"
            modifier = { message ->
                message.replace("T", "7").replace("L", "1").replace("E", "3")
            }
        }

        7 -> {
            mood = "poetic"
            modifier = { message ->
                val space = Random.nextInt(1..5)
                message.split(" ").take(space).joinToString(" ") + "\n" + message.split(" ").drop(space).joinToString(" ")
            }
        }

        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }
    }
    narrationModifier = modifier
    narrate("The narrator heads begins to feel $mood")
}
