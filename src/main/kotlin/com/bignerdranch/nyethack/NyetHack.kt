package com.bignerdranch.nyethack

lateinit var player: Player

fun main() {
    narrate("Welcome to NyetHack!")

    val playerName = Player.promptHeroName()

    player = Player(initialName = playerName, hometown = "Saint-Petersburg", isImmortal = false)

    changeNarratorMood()

    Game.play()
}
