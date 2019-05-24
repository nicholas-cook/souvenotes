package com.souvenotes.souvenotes.registration.policies

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.res.AssetManager
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

class PrivacyPolicyViewModel : ViewModel() {

    companion object {
        private val LOG_TAG = PrivacyPolicyViewModel::class.java.simpleName
    }

    private val ioScope = CoroutineScope(Dispatchers.IO)

    val policyText = MutableLiveData<Spanned?>()
    val progressVisible = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()

    init {
        policyText.value = null
        progressVisible.value = true
        showError.value = false
    }

    fun loadText(assets: AssetManager) {
        ioScope.launch {
            try {
                val policyString = getPrivacyText(assets)
                policyText.postValue(getHtml(policyString))
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error parsing privacy policy: ${e.message}")
                showError.postValue(false)
            } finally {
                progressVisible.postValue(false)
            }
        }
    }

    private fun getPrivacyText(assets: AssetManager): String {
        val policyBuilder = StringBuilder()
        val inputStream = assets.open("souvenotes_privacy_policy.txt")
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var nextLine = bufferedReader.readLine()
        while (nextLine != null) {
            policyBuilder.append(nextLine)
            nextLine = bufferedReader.readLine()
        }
        bufferedReader.close()
        inputStreamReader.close()
        inputStream.close()
        return policyBuilder.toString()
    }

    private fun getHtml(html: String): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, 0)
        }
        return Html.fromHtml(html)
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}