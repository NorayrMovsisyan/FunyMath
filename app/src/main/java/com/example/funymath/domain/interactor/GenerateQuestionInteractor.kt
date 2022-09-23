package com.example.funymath.domain.interactor

import com.example.funymath.data.model.GameSettings
import com.example.funymath.data.model.Question

interface GenerateQuestionInteractor {
    operator fun invoke(maxSumValue:Int):Question
}