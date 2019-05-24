package com.souvenotes.souvenotes.registration.policies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.souvenotes.souvenotes.R
import kotlinx.android.synthetic.main.activity_terms_of_service.*

/**
 * Created on 12/17/17.
 */
class TermsAndConditionsActivity : AppCompatActivity() {

    companion object {
        private const val FILE_NAME = "souvenotes_terms_of_service.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.title_terms_and_conditions)

        val viewModel = ViewModelProviders.of(this).get(PoliciesViewModel::class.java)

        viewModel.progressVisible.observe(this, Observer {
            it?.let { visible ->
                progress_bar.visibility = if (visible) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        })

        viewModel.policyText.observe(this, Observer {
            terms_and_conditions.text = it
        })

        viewModel.showError.observe(this, Observer {
            if (it == true) {
                showErrorDialog()
            }
        })

        viewModel.loadText(assets, FILE_NAME)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle(R.string.title_error)
            setMessage(R.string.error_terms)
            setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            setOnCancelListener { dialog ->
                dialog.dismiss()
                finish()
            }
        }
        builder.show()
    }
}