package ru.topbun.rapid.presentation.screens.auth.fragments.login

data class LoginState(
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val validFields: Boolean = false,
    val isAuth: Boolean = false,
    val loginScreenState: LoginScreenState = LoginScreenState.Initial
){
    
    sealed interface LoginScreenState{
        
        data object Initial: LoginScreenState
        data object Loading: LoginScreenState
        data object Success: LoginScreenState
        data class Error(val msg: String): LoginScreenState
        
    }
    
}
