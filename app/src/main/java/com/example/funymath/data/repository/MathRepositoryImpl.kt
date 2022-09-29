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
                    10,
                    3,
                    50,
                    8
                )
            }
            Level.Easy -> {
                GameSettings(
                    10,
                    10,
                    70,
                    60
                )
            }
            Level.Normal -> {
                GameSettings(
                    20,
                    20,
                    80,
                    40
                )
            }
            Level.Hard -> {
                GameSettings(
                    30,
                    30,
                    90,
                    40
                )
            }
        }
    }

    override fun generateQuestion(maxSumValue: Int, countOfOption: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        return if (sum > MIN_SUM_VALUE) {
            val visibleNumber = Random.nextInt(MIN_SUM_VALUE, sum)
            val options = HashSet<Int>()
            val rightAnswer = sum - visibleNumber
            options.add(rightAnswer)
            val from = max(rightAnswer - countOfOption, MIN_ANSWER_VALUE)
            val to = min(maxSumValue, rightAnswer + countOfOption)
            while (options.size < countOfOption) {
                options.add(Random.nextInt(from, to))
            }
            Question(sum, visibleNumber, options.toList())
        } else Question(0, 0, emptyList())
    }

    private companion object {
        private const val MIN_SUM_VALUE = 1
        private const val MIN_ANSWER_VALUE = 1
    }
}