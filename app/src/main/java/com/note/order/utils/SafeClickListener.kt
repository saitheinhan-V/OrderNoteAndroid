package com.note.order.utils

import android.os.SystemClock
import android.view.View

//To handle multiple clicks on Button
class SafeClickListener(
    private var defaultInterval: Int = 5000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}