package ru.topbun.rapid.presentation.screens.auth.fragments.signUp

data class SignUpState(
    val username: String = "",
    val usernameError: Boolean = false,
    val phone: String = "",
    val phoneError: Boolean = false,
    val email: String = "",
    val emailError: Boolean = false,
    val password: String = "",
    val passwordError: Boolean = false,
    val confirmPassword: String = "",
    val confirmPasswordError: Boolean = false,
    val showPassword: Boolean = false,
    val showConfirmPassword: Boolean = false,
    val validFields: Boolean = false,
    val isAuth: Boolean = false,
    val signUpScreenState: SignUpScreenState = SignUpScreenState.Initial
){
    
    sealed interface SignUpScreenState{
        
        data object Initial: SignUpScreenState
        data object Loading: SignUpScreenState
        data object Success: SignUpScreenState
        data class Error(val msg: String): SignUpScreenState
        
    }
    
}
