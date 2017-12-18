package com.souvenotes.souvenotes.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Created on 10/11/17.
 */
open class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }
}