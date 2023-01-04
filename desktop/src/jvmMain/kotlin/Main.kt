import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.tri_tail.ceal_chronicler.koin.KoinModules
import com.tri_tail.ceal_chronicler.ui.main_view.DisplayMainView
import org.koin.core.context.startKoin

fun main() {
    application {
        val windowState = rememberWindowState()

        val koinApplication = startKoin {
            modules(KoinModules.models, KoinModules.repositories)
            printLogger()
        }
        val koin = koinApplication.koin

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Ceal Chronicler"
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                DisplayMainView(koin)
            }
        }
    }
}