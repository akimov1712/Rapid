package ru.topbun.rapid.presentation

import android.Manifest.permission.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.topbun.rapid.presentation.theme.colorScheme
import ru.topbun.rapid.presentation.screens.auth.AuthScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(MaterialTheme.colorScheme.primary)
                requestPermissionNotify()
                Navigator(AuthScreen){
                    FadeTransition(it, animationSpec = tween(200))
                }
            }
        }
    }

    @Composable
    private fun requestPermissionNotify(){
        val contract = ActivityResultContracts.RequestMultiplePermissions()
        val launcher = rememberLauncherForActivityResult(contract = contract) {}
        SideEffect {
            launcher.launch(arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION) )
        }
    }


}