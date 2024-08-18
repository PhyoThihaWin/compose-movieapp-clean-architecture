package com.pthw.composemovieappcleanarchitecture

import android.app.Application
import com.pthw.shared.extension.isInDev
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by P.T.H.W on 25/03/2024.
 */
@HiltAndroidApp
class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }
}

private fun setupTimber() {
    if (!BuildConfig.DEBUG) return
    Timber.plant(object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            val prefix = super.createStackElementTag(element)?.substringBefore("$") ?: "Timber"
            return String.format("C:%s, L:%s", prefix, element.lineNumber, element.methodName)
        }
    })
}