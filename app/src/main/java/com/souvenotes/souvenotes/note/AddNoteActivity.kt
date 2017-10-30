package com.souvenotes.souvenotes.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.widget.Toast
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.NoteModel
import com.souvenotes.souvenotes.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_add_note.*

/**
 * Created by NicholasCook on 10/23/17.
 */
class AddNoteActivity : AppCompatActivity(), IAddNotesContract.View {

    private lateinit var addNotePresenter: AddNotePresenter
    private val noteModel = NoteModel()

    private var noteKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        noteKey = savedInstanceState?.getString(EXTRA_NOTE_KEY) ?: intent?.getStringExtra(
                EXTRA_NOTE_KEY)

        setTitle(if (noteKey != null) R.string.title_edit_note else R.string.title_add_note)

        addNotePresenter = AddNotePresenter(this, noteKey)
        addNotePresenter.start()

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_NOTE_KEY, noteKey)
        super.onSaveInstanceState(outState)
    }

    override fun onAddNoteError(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onNoteLoaded(note: NoteModel) {
        note_title.setText(note.title)
        note_content.setText(note.content)
    }

    override fun onLoadNoteError() {
        Toast.makeText(this, R.string.note_load_error, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStop() {
        addNotePresenter.saveNote(note_title.text.toString(), note_content.text.toString())
        addNotePresenter.stop()
        super.onStop()
    }

    companion object {
        private const val EXTRA_NOTE_KEY = "com.souvenotes.souvenotes.note"

        fun editNote(context: Context, noteKey: String) {
            val edit = Intent(context, AddNoteActivity::class.java).apply {
                putExtra(EXTRA_NOTE_KEY, noteKey)
            }
            context.startActivity(edit)
        }
    }
}