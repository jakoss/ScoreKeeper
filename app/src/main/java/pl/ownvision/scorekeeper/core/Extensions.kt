package pl.ownvision.scorekeeper.core

import android.app.Activity
import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.InputType
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.tapadoo.alerter.Alerter
import pl.ownvision.scorekeeper.R

/**
 * Created by jakub on 31.05.2017.
 */

fun Context.toast(text: String, duration: Int = Toast.LENGTH_LONG) = Toast.makeText(this, text, duration).show()

fun Activity.snackbar(@StringRes text: Int, duration: Int = Snackbar.LENGTH_LONG) {
    snackbar(getString(text), duration)
}

fun Activity.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    val view = this.findViewById(android.R.id.content)
    val snackBar = Snackbar.make(view, text, duration)

    snackBar.setAction("OK") { snackBar.dismiss() }
    snackBar.show()
}

fun Activity.showInputDialog(@StringRes title: Int, @StringRes positive: Int, placeholder: String, value: String?, callback: (input: CharSequence) -> Unit) {
    MaterialDialog.Builder(this)
            .title(title)
            .inputType(InputType.TYPE_CLASS_TEXT)
            .input(placeholder, value, { _, input ->
                callback(input)
            })
            .positiveText(positive)
            .show()
}

fun Activity.alert(title: String?, text: String) {
    if(title != null){
        Alerter.create(this)
                .setTitle(title)
                .setText(text)
                .setBackgroundColor(R.color.colorPrimary)
                .show()
    }else {
        Alerter.create(this)
                .setText(text)
                .setBackgroundColor(R.color.colorPrimary)
                .show()
    }
}