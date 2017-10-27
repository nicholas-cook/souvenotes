package com.souvenotes.souvenotes.list

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.common.ChangeEventType
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.souvenotes.souvenotes.R
import com.souvenotes.souvenotes.models.NoteListModel
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
        holder.itemView.note_date.text = "${holder.itemView.context.getString(
                R.string.last_updated)} ${DateTimeUtils.getDisplayFormat(model.timestamp)}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotesViewHolder(inflater.inflate(R.layout.notes_list_item, parent, false))
    }

    override fun onError(error: DatabaseError?) {
        super.onError(error)
        Log.e("Adapter", error?.message)
    }

    override fun onChildChanged(type: ChangeEventType?, snapshot: DataSnapshot?, newIndex: Int,
                                oldIndex: Int) {
        super.onChildChanged(type, snapshot, newIndex, oldIndex)
        activity.setViewVisibility(itemCount > 0)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}