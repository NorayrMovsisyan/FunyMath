package com.example.funymath.data.database

import com.example.funymath.data.model.GameSettings
import com.example.funymath.data.model.Level
import com.example.funymath.data.model.Question

interface MathRepository {
    fun generateQuestion(maxSumValue: Int, countOfOption: Int): Question
    fun getGameSetting(level: Level): GameSettings
}