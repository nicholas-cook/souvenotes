package com.souvenotes.souvenotes.list

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by NicholasCook on 10/15/17.
 */
class NotesListPresenter(private val listView: IListContract.View?) : IListContract.Presenter {

    override fun onChildChanged(itemCount: Int) {
        listView?.setEmptyViewVisibility(itemCount <= 0)
        listView?.setListVisibility(itemCount > 0)
        listView?.setAddButtonClickListener(itemCount >= 100)
    }

    override fun deleteNote(listRef: DatabaseReference, noteKey: String) {
        listRef.removeValue { error, _ ->
            error?.let {
                listView?.showNoteDeletionError()
            }
        }
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseDatabase.getInstance().reference.child("notes").child(userId).child(
                noteKey).removeValue { error, _ ->
            error?.let {
                listView?.showNoteDeletionError()
            }
        }
    }
}