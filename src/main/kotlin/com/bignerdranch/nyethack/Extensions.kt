package com.bignerdranch.nyethack

fun <T> T.print(): T {
    println(this)
    return this
}

fun String.frame(padding: Int, formatChar: String = "*"): String {
    val middle = formatChar
        .padEnd(padding)
        .plus(this)
        .plus(formatChar.padStart(padding))

    val decoration = middle.indices.joinToString("") { formatChar }

    return "$decoration\n$middle\n$decoration"
}

fun String.middle(padding: Int, formatChar: String = " "): String {
    val paddingForMiddle = (padding - this.length) / 2
    return formatChar
        .padEnd(paddingForMiddle)
        .plus(this)
        .plus(formatChar.padStart(paddingForMiddle))
}

fun String.indentChar( attached: String, padding: Int, formatChar: Char = '.'): String {
    return this +  attached.padStart(padding - this.length, formatChar)
}

val String.numVowels
    get() = count { it.lowercase() in "aeiou" }
