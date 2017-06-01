package pl.ownvision.scorekeeper.core

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.widget.Toast

/**
 * Created by jakub on 31.05.2017.
 */

fun Context.toast(text: String, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(this, text, duration).show()

fun Activity.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    val view = this.findViewById(android.R.id.content)
    val snackBar = Snackbar.make(view, text, duration)

    snackBar.setAction("OK") { snackBar.dismiss() }
    snackBar.show()
}