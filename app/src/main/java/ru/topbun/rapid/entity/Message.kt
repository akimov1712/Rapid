package ru.topbun.rapid.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("messages")
data class Message(
    @PrimaryKey(true)
    val id: Int = 0,
    val questId: Int,
    val userId: Int,
    val text: String,
    val date: Long = System.currentTimeMillis()
): Parcelable
