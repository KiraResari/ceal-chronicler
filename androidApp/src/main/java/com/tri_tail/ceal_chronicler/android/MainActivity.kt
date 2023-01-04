package com.tri_tail.ceal_chronicler.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.tri_tail.ceal_chronicler.koin.KoinModules
import com.tri_tail.ceal_chronicler.ui.main_view.DisplayMainView
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val koinApplication = startKoin {
            modules(KoinModules.models, KoinModules.repositories)
            androidLogger()
        }
        val koin = koinApplication.koin

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DisplayMainView(koin)
                }
            }
        }
    }
}

