package com.divyamoza.assesmentdemo.base

import android.app.Application
import android.content.Context
import com.divyamoza.assesmentdemo.BuildConfig
import timber.log.Timber
import kotlin.properties.Delegates


class AssesmentDemoApp : Application() {
    companion object {
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}