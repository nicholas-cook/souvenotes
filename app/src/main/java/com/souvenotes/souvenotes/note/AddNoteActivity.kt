package com.souvenotes.souvenotes.note

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.NoteModel
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_add_note.*

/**
 * Created by NicholasCook on 10/23/17.
 */
class AddNoteActivity : AppCompatActivity(), IAddNotesContract.View {

    private val addNotePresenter = AddNotePresenter(this)
    private val noteModel = NoteModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        note_title.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                noteModel.title = s.toString()
            }
        })
        note_content.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                noteModel.content = s.toString()
            }
        })
    }

    override fun onAddNoteError(message: Int) {
        Snackbar.make(add_note_parent, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onNoteAdded() {
        finish()
    }

    override fun onStop() {
        addNotePresenter.saveNote(noteModel.title, noteModel.content)
        super.onStop()
    }
}