package pl.ownvision.scorekeeper.core

import android.app.Activity
import android.content.Context
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import android.text.InputType
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.tapadoo.alerter.Alerter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import pl.ownvision.scorekeeper.R

fun BaseFragment.snackbar(@StringRes text: Int, duration: Int = Snackbar.LENGTH_LONG) {
    snackbar(getString(text), duration)
}

fun BaseFragment.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    this.activity?.findViewById<View>(android.R.id.content)?.let { view ->
        val snackBar = Snackbar.make(view, text, duration)

        snackBar.setAction("OK") { snackBar.dismiss() }
        snackBar.show()
    }

}

fun Context.showInputDialog(@StringRes title: Int, @StringRes positive: Int, placeholder: String, value: String?, callback: (input: String) -> Unit) {
    MaterialDialog(this)
            .title(title)
            .input(hint = placeholder, prefill = value, inputType = InputType.TYPE_CLASS_TEXT) { _, input ->
                callback(input.toString())
            }
            .positiveButton(positive)
            .show()
}

fun BaseFragment.alertWithTitle(@StringRes title: Int, @StringRes text: Int) = alertWithTitle(getString(title), getString(text))
fun BaseFragment.alertWithTitle(title: String, text: String) {
    Alerter.create(this.activity)
            .setTitle(title)
            .setText(text)
            .enableSwipeToDismiss()
            .setBackgroundColorRes(R.color.colorPrimary)
            .show()
}

fun Activity.alert(@StringRes text: Int) = alert(getString(text))
fun Activity.alert(text: String) {
    Alerter.create(this)
            .setText(text)
            .enableSwipeToDismiss()
            .setBackgroundColorRes(R.color.colorPrimary)
            .show()
}

fun Activity.showAbout() {
    val libsArray = this.resources.getStringArray(R.array.libraries)
    LibsBuilder()
            .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
            .withActivityTitle(getString(R.string.about_application))
            .withAboutIconShown(true)
            .withAboutVersionShown(true)
            .withAutoDetect(false)
            .withLibraries(*libsArray)
            .start(this)
}

fun DateTime.getFormattedLocal(): String = DateTimeFormat.forStyle("SM").print(this)

fun <T> Collection<T>?.isNullOrEmpty() : Boolean {
    if (this == null) return true
    return this.isEmpty()
}