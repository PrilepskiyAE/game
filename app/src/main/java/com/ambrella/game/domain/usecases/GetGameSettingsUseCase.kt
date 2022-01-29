package com.ambrella.game.domain.usecases

import com.ambrella.game.domain.entity.GameSettings
import com.ambrella.game.domain.entity.Level
import com.ambrella.game.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}