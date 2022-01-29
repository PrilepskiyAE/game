package com.ambrella.game.domain.usecases

import com.ambrella.game.domain.entity.Question
import com.ambrella.game.domain.repository.GameRepository

class GenerateQuestionUseCase(private val repository: GameRepository) {

    operator fun invoke(maxSumValue:Int):Question{
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    private companion object{
        const val COUNT_OF_OPTIONS = 6
    }
}