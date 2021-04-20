package com.alexcarbonell.rickandmorty.ui.common.extensions

import android.content.Context
import android.util.DisplayMetrics

fun Int.toPx(context: Context): Int {
    return this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Int.toDp(context: Context): Int {
    return this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}
