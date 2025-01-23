package ru.topbun.rapid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val phone: String,
    val name: String,
    val email: String,
    val status: UserStatus = UserStatus.User,
    val password: String
)
