package ru.topbun.rapid.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToNewsDate() = SimpleDateFormat("MMM dd, yyyy").format(this)
fun Long.formatDate() = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(this)


fun String.toDate() = SimpleDateFormat("dd.MM.yyyy").parse(this) ?: Date()
