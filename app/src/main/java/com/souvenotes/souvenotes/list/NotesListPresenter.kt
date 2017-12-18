package com.souvenotes.souvenotes.list

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * Created on 10/15/17.
 */
class NotesListPresenter(private val listView: IListContract.View?) : IListContract.Presenter {

    override fun onChildChanged(itemCount: Int) {
        listView?.setEmptyViewVisibility(itemCount <= 0)
        listView?.setListVisibility(itemCount > 0)
        listView?.setAddButtonClickListener(itemCount >= 100)
    }

    override fun deleteNote(noteKey: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            listView?.logout()
        } else {
            val childUpdates = HashMap<String, Any?>()
            childUpdates.put("/notes/$userId/$noteKey", null)
            childUpdates.put("/notes-list/$userId/$noteKey", null)
            FirebaseDatabase.getInstance().reference.updateChildren(
                    childUpdates).addOnFailureListener {
                listView?.showNoteDeletionError()
            }
        }
    }
}