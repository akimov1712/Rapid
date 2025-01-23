package ru.topbun.rapid.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    val quest: Question,
    val messages: List<Message>
): Parcelable
