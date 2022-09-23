package com.example.funymath.data.repository

import com.example.funymath.data.database.MathRepository
import com.example.funymath.data.model.GameSettings
import com.example.funymath.data.model.Level
import com.example.funymath.data.model.Question
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class MathRepositoryImpl : MathRepository {
    override fun getGameSetting(level: Level): GameSettings {
        return when (level) {
            Level.Test -> {
                GameSettings(
                    5,
                    5,
                    50,
                    120
                )
            }
            Level.Easy -> {
                GameSettings(
                    10,
                    5,
                    70,
                    120
                )
            }
            Level.Normal -> {
                GameSettings(
                    10,
                    10,
                    70,
                    90
                )
            }
            Level.Hard -> {
                GameSettings(
                    10,
                    10,
                    90,
                    60
                )
            }
        }
    }

    override fun generateQuestion(maxSumValue: Int, countOfOption: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_SUM_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOption, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOption)
        while (options.size < countOfOption) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    private companion object {
        private const val MIN_SUM_VALUE = 2
        private const val MIN_ANSWER_VALUE = 1
    }
}