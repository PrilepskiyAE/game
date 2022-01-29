package com.ambrella.game.domain.entity

data class GameResult (
    val winner:Boolean,
    val countOfRightAnswers:Int,
    val countOfRightQuestion: Int,
    val gameSettings: GameSettings
        )