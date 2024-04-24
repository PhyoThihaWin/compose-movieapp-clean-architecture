package com.pthw.shared.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.widget.Toast

/**
 * Created by P.T.H.W on 05/03/2024.
 */

fun String.isInDev(): Boolean = (this == "debug") || (this == "uat")

fun Context.showShortToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

val getDeviceModel get() = "${Build.MODEL}(Api-${Build.VERSION.SDK_INT})"

val Context.androidSecureId
    @SuppressLint("HardwareIds")
    get() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID).toString()

fun Float.roundTo(n : Int) : Float {
    return "%.${n}f".format(this).toFloat()
}

fun Double.roundTo(n : Int) : Double {
    return "%.${n}f".format(this).toDouble()
}