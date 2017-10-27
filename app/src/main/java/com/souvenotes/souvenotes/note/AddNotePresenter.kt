package com.souvenotes.souvenotes.note

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.souvenotes.souvenotes.models.NoteListModel
import com.souvenotes.souvenotes.models.NoteModel

/**
 * Created by NicholasCook on 10/23/17.
 */
class AddNotePresenter(private val addNoteView: IAddNotesContract.View?) :
        IAddNotesContract.Presenter {

    private val databaseRef = FirebaseDatabase.getInstance().reference

    override fun saveNote(title: String, content: String) {
        if (!title.isEmpty() && !content.isEmpty()) {
            val timestamp = System.currentTimeMillis()
            val notesValues = NoteModel(title, content, timestamp).toMap()
            val noteListValues = NoteListModel(title, timestamp).toMap()
            val childUpdates = HashMap<String, Any>()
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            val notesKey = databaseRef.child("notes").push().key
            childUpdates.put("/notes/$userId/$notesKey", notesValues)

            val notesListKey = databaseRef.child("notes-list").push().key
            childUpdates.put("/notes-list/$userId/$notesListKey", noteListValues)

            databaseRef.updateChildren(childUpdates).addOnFailureListener { exception ->
                //TODO: Notify activity
                Log.e("AddNote", exception.message)
            }
        }
    }
}