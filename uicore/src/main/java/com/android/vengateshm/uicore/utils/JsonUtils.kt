package com.android.vengateshm.uicore.utils

import android.content.Context

fun Context.readFromAsset(fileName: String): String {
    return this.assets.open(fileName).bufferedReader()
        .use { reader ->
            reader.readText()
        }
}