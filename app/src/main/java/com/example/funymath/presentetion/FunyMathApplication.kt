package com.example.funymath.presentetion

import android.content.Context
import com.example.funymath.utils.ApplicationLifeCycle

class FunyMathApplication: ApplicationLifeCycle {
    companion object{
            var activityOnScreen = false
            var mainActivityOnScreen = false
            lateinit var appContext: Context

        }

}