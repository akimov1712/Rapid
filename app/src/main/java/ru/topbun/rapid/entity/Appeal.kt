package ru.topbun.rapid.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("appeals")
data class Appeal(
    @PrimaryKey(true)
    val id: Int = 0,
    val userId: Int,
    val date: Long = System.currentTimeMillis(),
    val title: String,
    val category: AppealCategory?,
    val descr: String,
    val status: AppealStatus = AppealStatus.Progress,
    val image: String?,
    val lat: Double?,
    val lon: Double?
): Parcelable
