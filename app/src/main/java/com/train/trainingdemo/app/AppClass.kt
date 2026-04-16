package com.train.trainingdemo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration

@HiltAndroidApp
class AppClass : Application(){
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().userAgentValue = packageName

    }
}