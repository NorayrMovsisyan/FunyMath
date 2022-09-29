package com.example.funymath.presentetion.mathgame.mathgame

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.funymath.appbase.viewmodel.BaseViewModel
import com.example.funymath.data.model.GameResult
import com.example.funymath.data.model.GameSettings
import com.example.funymath.data.model.Level
import com.example.funymath.data.model.Question
import com.example.funymath.domain.interactor.GenerateQuestionInteractor
import com.example.funymath.domain.interactor.GetGameSettingInteractor
import kotlinx.coroutines.launch

class MathGameViewModel(
    private val generateQuestionInteractor: GenerateQuestionInteractor,
    private val getGameSettingInteractor: GetGameSettingInteractor
) :
    BaseViewModel() {
    private var timer: CountDownTimer? = null

    private val _questions = MutableLiveData<Question>()
    val questions: LiveData<Question>
        get() = _questions


    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private var countOfRightAnswer = 0
    private var countOfQuestion = 0

    private val _gameSettingsState = MutableLiveData<GameSettings>()
    val gameSettingsState: LiveData<GameSettings>
        get() = _gameSettingsState

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String>
        get() = _progressAnswer

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult


    fun endGame() {
        if (countOfQuestion == _gameSettingsState.value?.maxSumValue)
            finishGame()
    }

    fun setLevel(level: Level) {
        viewModelScope.launch {
            _gameSettingsState.postValue(getGameSettingInteractor(level))
            _gameSettingsState.value?.let {
                generateQuestion(it.maxSumValue)
            }
        }
    }

    private fun generateQuestion(maxSum: Int) {
        viewModelScope.launch {
            _questions.postValue(generateQuestionInteractor(maxSum))
            gameSettingsState.value?.let {
                statTimer(it.gameTimeInSeconds.times(1000L))
            }
        }
    }

    private fun statTimer(times: Long) {
        timer =
            object : CountDownTimer(
                times,
                1000L
            ) {
                override fun onTick(p0: Long) {
                    _formattedTime.postValue((formattedTimeInMilliseconds(p0)))

                }

                override fun onFinish() {
                    finishGame()
                }

            }
        timer?.start()
    }

    private fun finishGame() {
        viewModelScope.launch {
            _gameSettingsState.value?.let { it1 ->
                _enoughCount.value?.let {
                    GameResult(
                        winner = it,
                        countOfRightAnswer,
                        countOfQuestion,
                        it1
                    )
                }
            }?.let { it2 -> _gameResult.postValue(it2) }
        }

    }


    fun formattedTimeInMilliseconds(milliseconds: Long): String {
        val seconds = milliseconds / 1000L
        val minutes = milliseconds / 1000 / 60
        val leftSeconds = seconds - (minutes * 1000)
        return String.format("%02d:%02d", minutes, leftSeconds)

    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswer(_gameSettingsState.value?.maxSumValue ?: 0)
        viewModelScope.launch {
            _percentOfRightAnswer.postValue(percent)
            _enoughCount.postValue(
                countOfRightAnswer >= (_gameSettingsState.value?.minCountOfRightAnswers ?: 0)
            )
        }

    }

    private fun calculatePercentOfRightAnswer(questionCount: Int): Int {
        return if (questionCount > 0)
            ((countOfRightAnswer / questionCount.toDouble()) * 100).toInt()
        else 0
    }

    fun chooseAnswer(number: Int) {
        val rightAnswer = _questions.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswer++
        }
        countOfQuestion++
        if (countOfQuestion <= (_gameSettingsState.value?.maxSumValue ?: 0)) {
            generateQuestion(_gameSettingsState.value?.maxSumValue ?: 0)
            updateProgress()
        } else finishGame()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}

