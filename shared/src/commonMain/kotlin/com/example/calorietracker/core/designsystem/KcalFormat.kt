package com.example.calorietracker.core.designsystem

fun Int.formatWithCommas(): String {
    val str = toString()
    val result = StringBuilder()
    var count = 0
    for (i in str.lastIndex downTo 0) {
        if (count > 0 && count % 3 == 0) result.insert(0, ',')
        result.insert(0, str[i])
        count++
    }
    return result.toString()
}

fun Int.toKcalString(): String = "${this.formatWithCommas()} kcal"

fun formatProgress(current: Int, goal: Int): String = "${current.formatWithCommas()} / ${goal.formatWithCommas()} kcal"

fun formatOverGoal(excess: Int): String = "Over goal by ${excess.formatWithCommas()} kcal"
