package com.ambrella.game.domain.repository

import com.ambrella.game.domain.entity.GameSettings
import com.ambrella.game.domain.entity.Level
import com.ambrella.game.domain.entity.Question

interface GameRepository {
    fun generateQuestion(
       maxSumValue: Int,
       countOfOptions: Int
    ):Question
    fun getGameSettings(level: Level):GameSettings

}