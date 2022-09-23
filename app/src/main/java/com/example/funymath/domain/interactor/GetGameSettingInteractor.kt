package com.example.funymath.domain.interactor

import com.example.funymath.data.model.GameSettings
import com.example.funymath.data.model.Level


interface GetGameSettingInteractor {
    operator fun invoke(level: Level):GameSettings
}