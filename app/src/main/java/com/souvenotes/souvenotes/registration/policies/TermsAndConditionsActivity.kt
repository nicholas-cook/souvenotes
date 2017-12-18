package com.souvenotes.souvenotes.registration.policies

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import com.souvenotes.souvenotes.R
import kotlinx.android.synthetic.main.activity_terms_of_service.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

/**
 * Created on 12/17/17.
 */
class TermsAndConditionsActivity : AppCompatActivity() {

    private var parseJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_terms_and_conditions)
    }

    override fun onStart() {
        super.onStart()
        progress_bar.visibility = View.VISIBLE
        parseJob = loadPolicy()
    }

    override fun onStop() {
        progress_bar.visibility = View.GONE
        parseJob?.cancel()
        super.onStop()
    }

    private fun loadPolicy(): Job = async(UI) {
        try {
            var privacyHtml: Spanned? = null
            val job = async(CommonPool) {
                privacyHtml = getHtml(getPrivacyText())
            }
            job.await()
            progress_bar.visibility = View.GONE
            terms_and_conditions.text = privacyHtml
            terms_and_conditions.movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            progress_bar.visibility = View.GONE
            e.printStackTrace()
            val builder = AlertDialog.Builder(this@TermsAndConditionsActivity).apply {
                setTitle(R.string.title_error)
                setMessage(R.string.error_privacy)
                setPositiveButton(android.R.string.ok, { dialog, _ ->
                    dialog.dismiss()
                    finish()
                })
                setOnCancelListener { dialog ->
                    dialog.dismiss()
                    finish()
                }
            }
            builder.show()
        }
    }

    private fun getPrivacyText(): String {
        val policyBuilder = StringBuilder()
        val assetManager = assets
        val inputStream = assetManager.open("souvenotes_terms_of_service.txt")
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}