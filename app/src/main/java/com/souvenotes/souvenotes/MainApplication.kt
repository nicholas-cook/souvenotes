package com.souvenotes.souvenotes

import android.support.multidex.MultiDexApplication
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import net.danlew.android.joda.JodaTimeAndroid

/**
 * Created on 10/26/17.
 */
class MainApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        MobileAds.initialize(this, "ca-app-pub-5268862472871083~6197425733")
        JodaTimeAndroid.init(this)
    }
}