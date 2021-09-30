package ru.skillbox.presentation_patterns.utils.extension

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun Context.getCompatColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getCompatDrawable(@DrawableRes drawable: Int) =
        AppCompatResources.getDrawable(this, drawable)

fun Context.getStringResource(@StringRes strId: Int, param: Any): String = String.format(getString(strId), param)