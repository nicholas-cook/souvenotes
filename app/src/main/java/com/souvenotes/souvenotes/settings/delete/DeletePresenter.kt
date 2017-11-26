package com.souvenotes.souvenotes.settings.delete

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.souvenotes.souvenotes.R

/**
 * Created on 11/25/17.
 */
class DeletePresenter(private var deleteView: IDeleteContract.View?) : IDeleteContract.Presenter {

    override fun onDeletionConfirmed() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            deleteView?.logout()
        } else {
           deleteData(user)
        }
    }

    private fun deleteData(user: FirebaseUser) {
        val childUpdates = HashMap<String, Any?>()
        childUpdates.put("/notes/${user.uid}", null)
        childUpdates.put("/notes-list/${user.uid}", null)
        FirebaseDatabase.getInstance().reference.updateChildren(
                childUpdates).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                deleteUser(user)
            } else {
                deleteView?.onDeletionError(R.string.delete_error)
            }
        }
    }

    private fun deleteUser(user: FirebaseUser) {
        user.delete().addOnCompleteListener { result ->
            if (result.isSuccessful) {
                deleteView?.onAccountDeleted()
            } else {
                deleteView?.onDeletionError(R.string.delete_error)
            }
        }
    }

    override fun nullifyView() {
        deleteView = null
    }
}