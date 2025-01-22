package ru.topbun.rapid.presentation.screens.submit

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.rapid.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.entity.Appeal
import ru.topbun.rapid.entity.AppealCategory
import ru.topbun.rapid.repository.AppealRepository
import ru.topbun.rapid.repository.UserRepository

class SubmitViewModel(context: Context, intent: IntentOpenSubmit): ScreenModelState<SubmitState>(SubmitState(intent = intent)) {

    private val repository = AppealRepository(context)
    private val userRepository = UserRepository(context)

    init {
        val intent = stateValue.intent
        if (intent is IntentOpenSubmit.Edit){
            updateState {
                copy(
                    id = intent.appeal.id,
                    title = intent.appeal.title,
                    category = intent.appeal.category,
                    descr = intent.appeal.descr,
                    image = intent.appeal.image,
                    lat = intent.appeal.lat,
                    lon = intent.appeal.lon,
                )
            }
            updateValidFields()
        }
    }

    fun addAppeal() = screenModelScope.launch{
        val user = userRepository.getUserInfo() ?: return@launch
        val appeal = Appeal(
            id = stateValue.id,
            userId = user.id,
            title = stateValue.title,
            category = stateValue.category,
            descr = stateValue.descr,
            image = stateValue.image,
            lat = stateValue.lat,
            lon = stateValue.lon,
            date = System.currentTimeMillis()
        )
        repository.addAppeal(appeal)
        updateState { SubmitState(shouldCloseScreen = true, intent = intent) }
    }

    fun deleteAppeal() = screenModelScope.launch{
        val intent = stateValue.intent
        if (intent is IntentOpenSubmit.Edit){
            repository.deleteAppeal(intent.appeal.id)
            updateState { SubmitState(shouldCloseScreen = true, intent = intent) }
        }
    }

    fun changeTitle(title: String){
        updateState { copy(title = title) }
        updateValidFields()
    }
    fun changeCategory(category: AppealCategory?){
        updateState { copy(category = category) }
        updateValidFields()
    }
    fun changeDescr(descr: String){
        updateState { copy(descr = descr) }
        updateValidFields()
    }
    fun changeImage(image: String?){
        updateState { copy(image = image) }
        updateValidFields()
    }
    fun changeMapPoint(lat: Double?, lon: Double?){
        updateState { copy(lat = lat, lon = lon) }
        updateValidFields()
    }
    fun changeScrollState(state: Boolean){
        updateState { copy(scrollEnabled = state) }
        updateValidFields()
    }

    private fun updateValidFields() = updateState {
        copy(fieldsValid = fieldsIsValid())
    }

    private fun fieldsIsValid(): Boolean{
        if (stateValue.title.isEmpty()) return false
        if (stateValue.category == null) return false
        if (stateValue.image == null) return false
        if (stateValue.lat == null) return false
        return true
    }

}