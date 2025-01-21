package ru.topbun.rapid.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Date.formatToNewsDate() = SimpleDateFormat("MMM dd, yyyy").format(this)

fun String.toDate() = SimpleDateFormat("dd.MM.yyyy").parse(this) ?: Date()
