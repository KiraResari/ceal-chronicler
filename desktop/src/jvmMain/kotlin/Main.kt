import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.tri_tail.ceal_chronicler.ui.main_view.MainView

fun main() {
    application {
        val windowState = rememberWindowState()

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Ceal Chronicler"
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                MainView()
            }
        }
    }
}