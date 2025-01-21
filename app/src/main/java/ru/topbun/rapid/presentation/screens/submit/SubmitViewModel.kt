package ru.topbun.rapid.presentation.screens.submit

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.pawmate.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.entity.Appeal
import ru.topbun.rapid.entity.AppealCategory
import ru.topbun.rapid.repository.AppealRepository
import ru.topbun.rapid.repository.UserRepository

class SubmitViewModel(context: Context): ScreenModelState<SubmitState>(SubmitState()) {

    private val repository = AppealRepository(context)
    private val userRepository = UserRepository(context)

    fun addAppeal() = screenModelScope.launch{
        val user = userRepository.getUserInfo() ?: return@launch
        val appeal = Appeal(
            userId = user.id,
            title = stateValue.title,
            category = stateValue.category,
            descr = stateValue.descr,
            image = stateValue.image,
            lat = stateValue.lat,
            lon = stateValue.lon
        )
        repository.addAppeal(appeal)
        updateState { SubmitState() }
    }

    fun changeTitle(title: String) = updateState { copy(title = title) }
    fun changeCategory(category: AppealCategory?) = updateState { copy(category = category) }
    fun changeDescr(descr: String) = updateState { copy(descr = descr) }
    fun changeImage(image: String?) = updateState { copy(image = image) }
    fun changeMapPoint(lat: Double?, lon: Double?) = updateState { copy(lat = lat, lon = lon) }

}