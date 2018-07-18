package com.vyakunin.testapp.ext

import android.support.v4.app.Fragment
import android.widget.Toast

fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any>): T {
    arguments = bundleOf(*params)
    return this
}

fun Fragment.toast(resId: Int) {
    Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}