package com.example.funymath.domain.usecase

import com.example.funymath.data.database.MathRepository
import com.example.funymath.data.model.Question
import com.example.funymath.domain.interactor.GenerateQuestionInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenerateQuestionUseCase(private val mathRepository: MathRepository) :
    GenerateQuestionInteractor {
    override  fun invoke(maxSumValue: Int): Question {
        return mathRepository.generateQuestion(maxSumValue, COUNT_OF_OPTION)
    }

    private companion object {
        private const val COUNT_OF_OPTION = 6
    }
}