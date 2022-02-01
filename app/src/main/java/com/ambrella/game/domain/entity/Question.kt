package com.ambrella.game.domain.entity

data class Question(
    val sum:Int,
    val visibleNumber:Int,
    val option:List<Int>
){

    val rightAnswer: Int
        get() = sum - visibleNumber
}