package com.souvenotes.souvenotes

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import net.danlew.android.joda.JodaTimeAndroid

/**
 * Created on 10/26/17.
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111")
        JodaTimeAndroid.init(this)
    }
}