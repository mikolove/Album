package com.mikolove.album

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

class ApplicationController : Application(){

    override fun onCreate() {
        super.onCreate()

        //Backward compatibility for vector old SDK
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Timber.plant(Timber.DebugTree())
    }
}