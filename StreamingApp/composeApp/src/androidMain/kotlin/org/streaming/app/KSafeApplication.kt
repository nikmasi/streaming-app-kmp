package org.streaming.app

import android.app.Application
import org.streaming.app.encrypted.AppModule

class KSafeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}