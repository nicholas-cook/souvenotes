package com.souvenotes.souvenotes.utils

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created on 10/15/17.
 */
fun Context.hideKeyboard(view: View?) {
    view?.let {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.hideKeyboard(view: View?) {
    view?.let {
        val inputManager = activity?.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
