package com.android.vengateshm.uicore.utils

import android.os.Bundle
import androidx.annotation.IntDef

interface UiWatcher {
    fun onUiStatusListener(@Status status: Int, bundle: Bundle)

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [ENTER, EXIT, COMPLETED])
    annotation class Status
}

const val ENTER: Int = 0
const val EXIT: Int = 1
const val COMPLETED: Int = 2
