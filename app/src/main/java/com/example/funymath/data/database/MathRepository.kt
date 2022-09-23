package com.example.funymath.data.database

import com.example.funymath.data.model.GameSettings
import com.example.funymath.data.model.Level
import com.example.funymath.data.model.Question

interface MathRepository {
    fun getGameSetting(level: Level): GameSettings

    fun generateQuestion(masSumValue: Int, countOfOption: Int): Question
}