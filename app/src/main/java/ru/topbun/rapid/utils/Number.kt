package ru.topbun.rapid.utils

import android.util.Patterns

fun String.isValidMobile() = Regex("^\\+?[0-9]{10,15}\$").matches(this)