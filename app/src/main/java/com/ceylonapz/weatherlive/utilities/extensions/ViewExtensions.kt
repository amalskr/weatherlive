package com.infinity.movieapp.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun ProgressBar.hide() {
    this.visibility = View.INVISIBLE
}

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

//startActivityAndClearStack(MainActivity::class.java, null)
fun Context.startActivityAndClearStack(clazz: Class<*>, extras: Bundle?) {
    val intent = Intent(this, clazz)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    if (extras != null) {
        intent.putExtras(extras)
    }
    startActivity(intent)
}

fun Context.navigateActivity(clazz: Class<*>, extras: Bundle?, isFinish: Unit?) {
    val intent = Intent(applicationContext, clazz)
    if (extras != null) {
        intent.putExtras(extras)
    }
    startActivity(intent)
    isFinish
}