package ru.topbun.rapid.presentation.screens.submit

import ru.topbun.rapid.entity.AppealCategory

data class SubmitState(
    val title: String = "",
    val category: AppealCategory? = null,
    val descr: String = "",
    val image: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
)
