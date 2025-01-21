package ru.topbun.rapid.presentation.screens.auth.fragments.login

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.rapid.presentation.screens.auth.fragments.login.LoginState.LoginScreenState.*
import ru.topbun.pawmate.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.repository.UserRepository

class LoginViewModel(
    private val context: Context
): ScreenModelState<LoginState>(LoginState()) {

    private val repository = UserRepository(context)

    fun checkAuth() = screenModelScope.launch{
        val isAuth = repository.isUserAuth()
        updateState { copy(isAuth = isAuth) }
    }

    fun login() = screenModelScope.launch {
        updateState { copy(loginScreenState = Loading) }
        val email = stateValue.email.trim()
        val phone = stateValue.phone.trim()
        val password = stateValue.password.trim()
        val isAuth = repository.login(email, phone, password)
        if (isAuth){
            updateState { copy(loginScreenState = Success) }
        } else {
            updateState { copy(loginScreenState = Error("Пользователь не найден")) }
        }
    }

    fun changeEmail(email: String) = updateState { copy(email = email, validFields = validFields(email, phone, password)) }
    fun changePhone(phone: String) = updateState { copy(phone = phone, validFields = validFields(email, phone, password)) }
    fun changePassword(password: String) = updateState { copy(password = password, validFields = validFields(email, phone, password)) }
    fun changePasswordVisible() = updateState { copy(showPassword = !showPassword) }

    private fun validFields(email: String, phone: String, password: String) = password.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()

    init {
        checkAuth()
    }

}