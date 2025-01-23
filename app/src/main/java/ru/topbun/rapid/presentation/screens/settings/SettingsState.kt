package ru.topbun.rapid.presentation.screens.settings

data class SettingsState(
    val notifyEnabled: Boolean = false,
    val feedbackMessage: String = "",
)
