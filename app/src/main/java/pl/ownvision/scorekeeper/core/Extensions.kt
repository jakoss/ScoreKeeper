package pl.ownvision.scorekeeper.core

import android.app.Activity
import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.text.InputType
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import com.tapadoo.alerter.Alerter
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import pl.ownvision.scorekeeper.R


fun Context.toast(text: String, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(this, text, duration).show()

fun Activity.snackbar(@StringRes text: Int, duration: Int = Snackbar.LENGTH_LONG) {
    snackbar(getString(text), duration)
}

fun Activity.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    val view = this.findViewById<View>(android.R.id.content)
    val snackBar = Snackbar.make(view, text, duration)

    snackBar.setAction("OK") { snackBar.dismiss() }
    snackBar.show()
}

fun Activity.showInputDialog(@StringRes title: Int, @StringRes positive: Int, placeholder: String, value: String?, callback: (input: String) -> Unit) {
    MaterialDialog.Builder(this)
            .title(title)
            .inputType(InputType.TYPE_CLASS_TEXT)
            .input(placeholder, value, { _, input ->
                callback(input.toString())
            })
            .positiveText(positive)
            .show()
}

fun Activity.alertWithTitle(@StringRes title: Int, @StringRes text: Int) = alertWithTitle(getString(title), getString(text))
fun Activity.alertWithTitle(title: String, text: String) {
    Alerter.create(this)
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