package com.example.funymath.appbase.utils

import androidx.lifecycle.LifecycleOwner
import droid.telemed.mts.ru.telemed.refactor.appbase.viewmodel.FlowObserver
import kotlinx.coroutines.flow.Flow

inline fun <reified T> Flow<T>.observeInLifecycle(
    lifecycleOwner: LifecycleOwner
) = FlowObserver(lifecycleOwner, this)

