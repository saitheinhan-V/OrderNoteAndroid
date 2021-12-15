package com.note.order.utils

import android.graphics.Color
import java.util.*

public class ColorUtil {

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

}