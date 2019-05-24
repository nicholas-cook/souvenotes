package com.souvenotes.souvenotes.registration.policies

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.res.AssetManager
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class PoliciesViewModel : ViewModel() {

    companion object {
        private val LOG_TAG = PoliciesViewModel::class.java.simpleName
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

    fun loadText(assets: AssetManager, fileName: String) {
        ioScope.launch {
            try {
                val policyString = getPrivacyText(assets, fileName)
                policyText.postValue(getHtml(policyString))
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error parsing privacy policy: ${e.message}")
                showError.postValue(false)
            } finally {
                progressVisible.postValue(false)
            }
        }
    }

    private fun getPrivacyText(assets: AssetManager, fileName: String): String {
        val policyBuilder = StringBuilder()
        val inputStream = assets.open(fileName)
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