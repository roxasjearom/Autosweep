package com.loraxx.electrick.autosweep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.loraxx.electrick.autosweep.ui.login.LoginScreen
import com.loraxx.electrick.autosweep.ui.theme.Autosweep20Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Autosweep20Theme {
                LoginScreen(
                    onLoginClicked = {
                        //TODO implement login here
                    }, onForgotPasswordClicked = {
                        //TODO implement forgot password here
                    }
                )
            }
        }
    }
}
