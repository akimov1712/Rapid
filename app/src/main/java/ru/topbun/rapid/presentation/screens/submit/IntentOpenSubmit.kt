package ru.topbun.rapid.presentation.screens.submit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.topbun.rapid.entity.Appeal

sealed interface IntentOpenSubmit : Parcelable {

    @Parcelize
    data object Append: IntentOpenSubmit

    @Parcelize
    data class Edit(val appeal: Appeal): IntentOpenSubmit
}