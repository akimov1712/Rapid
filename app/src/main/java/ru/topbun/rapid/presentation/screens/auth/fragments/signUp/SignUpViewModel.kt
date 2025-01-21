package ru.topbun.rapid.presentation.screens.auth.fragments.signUp

import android.content.Context
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ru.topbun.rapid.presentation.screens.auth.fragments.signUp.SignUpState.SignUpScreenState.*
import ru.topbun.rapid.entity.User
import ru.topbun.pawmate.presentation.theme.components.ScreenModelState
import ru.topbun.rapid.repository.UserRepository
import ru.topbun.pawmate.utils.validationEmail
import ru.topbun.rapid.utils.isValidMobile

class SignUpViewModel(
    private val context: Context
): ScreenModelState<SignUpState>(SignUpState()) {

    private val repository = UserRepository(context)

    fun checkAuth() = screenModelScope.launch{
        val isAuth = repository.isUserAuth()
        updateState { copy(isAuth = isAuth) }
    }

    fun signUp() = screenModelScope.launch {
        updateState { copy(signUpScreenState = Loading) }
        val user = User(
            name = stateValue.username.trim(),
            phone = stateValue.phone.trim(),
            email = stateValue.email.trim(),
            password = stateValue.password.trim(),
        )
        val isAuth = repository.singUp(user)
        if (isAuth){
            updateState { copy(signUpScreenState = Success) }
        } else {
            updateState { copy(signUpScreenState = Error("Пользователь уже зерегистрирован")) }
        }
    }

    fun changeUsername(username: String) {
        updateState {
            copy(
                username = username,
                usernameError = !username.validUsername()
            )
        }
        checkValidFields()
    }

    fun changeEmail(email: String) {
        updateState {
            copy(
                email = email,
                emailError = !email.validEmail()
            )
        }
        checkValidFields()
    }

    fun changePhone(phone: String) {
        updateState {
            copy(
                phone = phone,
                phoneError = !phone.validPhone()
            )
        }
        checkValidFields()
    }

    fun changePassword(password: String) {
        updateState {
            copy(
                password = password,
                passwordError = !password.validPassword()
            )
        }
        checkValidFields()
    }

    fun changeConfirmPassword(confirmPassword: String) {
        updateState {
            copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = !confirmPassword.validConfirmPassword()
            )
        }
        checkValidFields()
    }

    fun changePasswordVisible() = updateState {
        copy(showPassword = !showPassword)
    }


    fun changeConfirmPasswordVisible() = updateState {
        copy(showConfirmPassword = !showConfirmPassword)
    }


    private fun String.validUsername() = this.length in 2..48
    private fun String.validEmail() = this.validationEmail()
    private fun String.validPhone() = this.isValidMobile().also { println("Валидация телефона $this равно: $it") }
    private fun String.validPassword() = this.length in 6..64
    private fun String.validConfirmPassword() = this == stateValue.password

    private fun checkValidFields(){
        val validFields = listOf(
            stateValue.username.validUsername(),
            stateValue.email.validEmail(),
            stateValue.phone.validPhone(),
            stateValue.password.validPassword(),
            stateValue.confirmPassword.validConfirmPassword()
        ).contains(false)
        updateState { copy(validFields = !validFields) }
    }

}