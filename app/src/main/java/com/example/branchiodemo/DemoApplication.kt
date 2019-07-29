@file:Suppress("unused")

package com.example.branchiodemo

import android.app.Application

class DemoApplication : Application() {

    companion object {
        val TAG: String = DemoApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        GlobalLoggerUtils.showLog(TAG, "onCreate()")
        BranchUtils.init(this)
//        BranchUtils.generate(this)
    }
}