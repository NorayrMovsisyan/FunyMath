package com.example.funymath.domain.usecase

import com.example.funymath.data.database.MathRepository
import com.example.funymath.data.model.GameSettings
import com.example.funymath.data.model.Level
import com.example.funymath.domain.interactor.GetGameSettingInteractor

class GetGameSettingUseCase(private val mathRepository: MathRepository):GetGameSettingInteractor {
    override fun invoke(level: Level): GameSettings {
        return mathRepository.getGameSetting(level)
    }
}