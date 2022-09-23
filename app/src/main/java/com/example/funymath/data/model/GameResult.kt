package com.example.funymath.data.model

data class GameResult(
    val winner:Boolean,
    val countOfRightAnswers:Int,
    val countOfQuestion:Int,
    val gameSettings: GameSettings
)
