package ru.topbun.rapid.presentation.screens.appeals

import ru.topbun.rapid.entity.Appeal

data class AppealState(
    val appeals: List<Appeal> = emptyList()
)
