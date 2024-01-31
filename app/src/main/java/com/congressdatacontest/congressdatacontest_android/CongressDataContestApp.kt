package com.congressdatacontest.congressdatacontest_android

import android.app.Application

class CongressDataContestApp: Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object {
        lateinit var instance : CongressDataContestApp

        fun getInstance() : Application {
            return instance
        }
    }
}