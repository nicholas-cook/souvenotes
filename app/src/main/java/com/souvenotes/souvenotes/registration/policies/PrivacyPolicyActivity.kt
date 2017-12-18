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
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

/**
 * Created on 12/16/17.
 */
class PrivacyPolicyActivity : AppCompatActivity() {

    private var parseJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_privacy_policy)
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
            privacy_policy.text = privacyHtml
            privacy_policy.movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            progress_bar.visibility = View.GONE
            e.printStackTrace()
            val builder = AlertDialog.Builder(this@PrivacyPolicyActivity).apply {
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
        val inputStream = assetManager.open("souvenotes_privacy_policy.txt")
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