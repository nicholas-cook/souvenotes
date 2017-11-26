package com.souvenotes.souvenotes.settings.delete

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_delete.*

/**
 * Created on 11/25/17.
 */
class DeleteFragment : Fragment(), IDeleteContract.View {

    private var deletePresenter: IDeleteContract.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        deletePresenter = DeletePresenter(this)
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_delete.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun showDeleteConfirmation() {
        val builder = AlertDialog.Builder(activity).apply {
            setMessage(R.string.confirm_delete)
            setPositiveButton(R.string.option_delete, { dialog, _ ->
                dialog.dismiss()
                deletePresenter?.onDeletionConfirmed()
            })
            setNegativeButton(android.R.string.cancel, { dialog, _ ->
                dialog.dismiss()
            })
        }
        builder.show()
    }

    override fun onStop() {
        super.onStop()
        deletePresenter?.nullifyView()
    }

    override fun onAccountDeleted() {
        LoginActivity.logout(activity)
    }

    override fun onDeletionError(message: Int) {
        Snackbar.make(delete_parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun logout() {
        Toast.makeText(activity, R.string.log_back_in, Toast.LENGTH_LONG).show()
        LoginActivity.logout(activity)
    }

    companion object {
        fun newInstance(): DeleteFragment = DeleteFragment()
    }
}