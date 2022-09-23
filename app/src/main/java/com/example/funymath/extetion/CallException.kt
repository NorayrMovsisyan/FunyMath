package com.example.funymath.extetion

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import droid.telemed.mts.ru.telemed.core.CallException

fun CombinedLoadStates.toCallException() = (((refresh as LoadState.Error).error) as? CallException)