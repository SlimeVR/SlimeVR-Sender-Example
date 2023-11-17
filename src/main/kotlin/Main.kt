import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SlimeVR Sender Example",
        state = WindowState(width = 700.dp, height = 500.dp)
    ) {
        // Initialize a coroutine scope
        val composableScope = rememberCoroutineScope()

        // Initialize UDP
        val udpHandler = UDPHandler()
        composableScope.launch { udpHandler.heartbeat() }

        // Initialize GUI
        val guiHandler = GUIHandler(composableScope, udpHandler)
        guiHandler.initBody()
    }
}
