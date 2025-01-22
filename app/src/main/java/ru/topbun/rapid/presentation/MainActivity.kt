package ru.topbun.rapid.presentation

import android.Manifest
import android.Manifest.permission.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.topbun.pawmate.presentation.theme.colorScheme
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