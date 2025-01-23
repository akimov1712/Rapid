package ru.topbun.rapid.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("questions")
data class Question(
    @PrimaryKey(true)
    val id: Int = 0,
    val userId: Int,
    val title: String
): Parcelable
