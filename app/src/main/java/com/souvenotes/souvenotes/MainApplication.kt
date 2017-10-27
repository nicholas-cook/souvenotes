package com.souvenotes.souvenotes

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import net.danlew.android.joda.JodaTimeAndroid

/**
 * Created by NicholasCook on 10/26/17.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        JodaTimeAndroid.init(this)
    }
}