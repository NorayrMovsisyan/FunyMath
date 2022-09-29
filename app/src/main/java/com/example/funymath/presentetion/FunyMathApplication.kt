package com.example.funymath.presentetion

import android.app.Application
import android.content.Context
import com.example.funymath.BuildConfig
import com.example.funymath.data.database.MathRepository
import com.example.funymath.data.repository.MathRepositoryImpl
import com.example.funymath.domain.interactor.GenerateQuestionInteractor
import com.example.funymath.domain.interactor.GetGameSettingInteractor
import com.example.funymath.domain.usecase.GenerateQuestionUseCase
import com.example.funymath.domain.usecase.GetGameSettingUseCase
import com.example.funymath.presentetion.mathgame.mathgame.MathGameViewModel
import com.example.funymath.presentetion.mathgame.selectlevel.SelectLevelViewModel
import com.example.funymath.presentetion.mathgame.welcome.WelcomeViewModel
import com.example.funymath.utils.ApplicationLifeCycle
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module


class FunyMathApplication : Application(), ApplicationLifeCycle {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@FunyMathApplication)
            modules(listOf(repositoryModule, interactorModule, viewModelModule))
        }
    }

    private val repositoryModule = module {
        single<MathRepository> { MathRepositoryImpl() }
    }
    private val viewModelModule = module {
        viewModel { WelcomeViewModel() }
        viewModel { SelectLevelViewModel() }
        viewModel { MathGameViewModel(get(),get()) }
    }
    private val interactorModule = module {
        factory<GetGameSettingInteractor> { GetGameSettingUseCase(get()) }
        factory<GenerateQuestionInteractor> { GenerateQuestionUseCase(get()) }
    }

}