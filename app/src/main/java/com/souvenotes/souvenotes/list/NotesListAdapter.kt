package com.souvenotes.souvenotes.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.NoteListModel
import com.souvenotes.souvenotes.note.AddNoteActivity
import com.souvenotes.souvenotes.utils.DateTimeUtils
import kotlinx.android.synthetic.main.notes_list_item.view.*

/**
 * Created by NicholasCook on 10/15/17.
 */
class NotesListAdapter(private val activity: NotesListActivity,
                       options: FirebaseRecyclerOptions<NoteListModel>) :
        FirebaseRecyclerAdapter<NoteListModel, NotesListAdapter.NotesViewHolder>(options) {

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int, model: NoteListModel) {
        holder.itemView.note_title.text = if (model.title.isEmpty()) holder.itemView.context.getString(
                R.string.untitled) else model.title
        holder.itemView.note_date.text = holder.itemView.context.getString(R.string.last_updated,
                DateTimeUtils.getDisplayFormat(-1 * model.timestamp))
        holder.itemView.setOnClickListener {
            val notesKey = getRef(holder.adapterPosition).key
            AddNoteActivity.editNote(holder.itemView.context, notesKey)
        }
        holder.itemView.setOnLongClickListener {
            activity.showNoteDeletionConfirmation(getRef(holder.adapterPosition).key)
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(inflater.inflate(R.layout.notes_list_item, parent, false))
    }

    override fun onError(error: DatabaseError?) {
        super.onError(error)
        activity.showNotesError()
    }

    override fun onDataChanged() {
        activity.onChildChanged(itemCount)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}