package com.example.funymath.appbase.viewmodel

import android.content.Context
import android.provider.SyncStateContract
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.paging.PagingConfig
import com.example.funymath.presentetion.FunyMathApplication
import com.example.funymath.utils.Constants
import droid.telemed.mts.ru.telemed.core.ActionResult
import droid.telemed.mts.ru.telemed.core.CallException
import droid.telemed.mts.ru.telemed.core.ErrorApplicationVersion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.mp.KoinPlatformTools

abstract class BaseViewModel : ViewModel(), KoinComponent {
    private val _animateWhiteProgress = MutableSharedFlow<Boolean>(1)
    val animateWhiteProgress = _animateWhiteProgress.asSharedFlow()

    private val _messageError = MutableSharedFlow<String?>()
    val messageError = _messageError.asSharedFlow()

    private val _goToNewFragment: MutableSharedFlow<NavDirections> by lazy { MutableSharedFlow() }
    val goToNewFragment = _goToNewFragment.asSharedFlow()

    private val _navigateWithId: MutableSharedFlow<Int> by lazy { MutableSharedFlow() }
    val navigateWithId = _navigateWithId.asSharedFlow()

    private val _showDefaultError = MutableSharedFlow<String?>()
    val showDefaultError = _showDefaultError.asSharedFlow()

    private val _showUpdateApplication: MutableSharedFlow<Pair<String, String>> by lazy { MutableSharedFlow() }
    val showUpdateApplication = _showUpdateApplication.asSharedFlow()

    private val _callExceptionError = MutableSharedFlow<CallException>()
    val callExceptionError = _callExceptionError.asSharedFlow()

    protected val _showNetworkError: MutableSharedFlow<() -> Unit> by lazy { MutableSharedFlow() }
    val showNetworkError = _showNetworkError.asSharedFlow()

    fun animateWhiteProgress(it: Boolean, milliSeconds: Long = 500L, callback: () -> Unit = {}) {
        viewModelScope.launch {
            if (!it)
                delay(milliSeconds)
            _animateWhiteProgress.emit(it)
            callback()
        }
    }

    fun showErrorMessage(errorText: String?) {
        viewModelScope.launch {
            _messageError.emit(errorText)
        }
    }

    protected fun goToFragment(nav: NavDirections) {
        viewModelScope.launch {
            _goToNewFragment.emit(nav)
        }
    }

    protected fun navigateWithIdFragment(navigationId: Int) {
        viewModelScope.launch {
            _navigateWithId.emit(navigationId)
        }
    }


    protected fun showDefaultError(errorMessage: String?) {
        viewModelScope.launch {
            _showDefaultError.emit(errorMessage)
        }
    }

    fun <T> callData(
        result: ActionResult<T>, callback: () -> Unit = {}
    ): ActionResult<T> {
        return when (result) {
            is ActionResult.Success -> {
                result
            }
            is ActionResult.Error -> {

                animateWhiteProgress(false)

                when (result.errors.errorCode) {
                    Constants.ERROR_APPLICATION_VERSION -> {
                        (result.errors.errorBody as? ErrorApplicationVersion)?.let {
                            viewModelScope.launch {
                                _showUpdateApplication.emit(Pair(it.message ?: "", it.link ?: ""))
                            }
                        }
                    }
                    Constants.ERROR_NOT_NETWORK -> {
                        viewModelScope.launch {
                            _showNetworkError.emit {
                                callback()
                            }
                        }
                    }
                    else -> {
                        viewModelScope.launch {
                            _showDefaultError.emit(null)
                        }
                    }
                }

                ActionResult.Error(result.errors)
            }
        }
    }

    fun getBasePagingConfig(
        pageSize: Int = 10,
        prefetchDistance: Int = 10,
        initialLoadSize: Int = 30
    ) = PagingConfig(
        pageSize = pageSize,
        prefetchDistance = prefetchDistance,
        initialLoadSize = initialLoadSize
    )

    fun callExceptionError(callException: CallException?) {
        viewModelScope.launch {
            callException?.let {
                _callExceptionError.emit(callException)
            }
        }
    }

    fun getString(@StringRes strId: Int, vararg fmtArgs: Any?) =
        FunyMathApplication.appContext.getString(strId, *fmtArgs)

}