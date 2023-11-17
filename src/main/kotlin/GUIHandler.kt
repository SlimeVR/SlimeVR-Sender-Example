import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.reflect.KSuspendFunction0

class GUIHandler(private val composableScope: CoroutineScope, private val udpHandler: UDPHandler) {

    private val itemWidth = 160.dp
    private val itemHeight = 70.dp
    private val itemPadding = 8.dp

    @Composable
    fun initBody() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("SlimeVR Sender Example", color = Color.White) },
                    backgroundColor = MaterialTheme.colors.primary
                )
            },
            content = { displayContent() }
        )
    }

    @Composable
    private fun displayContent() {
        Column(Modifier.padding(itemPadding).fillMaxWidth()) {
            Row {
                var imuType = IMUType.BNO085.toString()
                var boardType = BoardType.SLIMEVR.toString()
                var mcuType = MCUType.ESP32.toString()

                suspend fun handshake(): String {
                    return udpHandler.handshake(
                        IMUType.valueOf(imuType),
                        BoardType.valueOf(boardType),
                        MCUType.valueOf(mcuType)
                    )
                }
                createButton("Handshake", "Searching...", ::handshake, false)

                fun imuSelected(value: String) {
                    imuType = value
                }
                createDropdown("IMU type", IMUType.getList(), imuType, ::imuSelected)

                fun boardSelected(value: String) {
                    boardType = value
                }
                createDropdown("Board type", BoardType.getList(), boardType, ::boardSelected)

                fun mcuSelected(value: String) {
                    mcuType = value
                }
                createDropdown("MCU type", MCUType.getList(), mcuType, ::mcuSelected)
            }

            Row {
                var imuAddType = IMUType.BNO085.toString()

                suspend fun addIMU(): String {
                    return udpHandler.addIMU(IMUType.valueOf(imuAddType))
                }
                createButton("Add IMU", null, ::addIMU)

                fun imuAddSelected(value: String) {
                    imuAddType = value
                }
                createDropdown("IMU type", IMUType.getList(), imuAddType, ::imuAddSelected)
            }

            Row {
                var imuID = 0

                suspend fun rotate(): String {
                    // TODO currently a random rotation for testing purposes
                    val rand = Random(System.currentTimeMillis())
                    return udpHandler.rotateIMU(
                        imuID,
                        Quaternion(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), rand.nextFloat()).unit()
                    )
                }
                createButton("Set rotation", null, ::rotate)

                fun imuIDChanged(value: String) {
                    imuID = value.toIntOrNull() ?: 0
                }
                createInput("IMU ID", imuID.toString(), ::imuIDChanged)
            }
        }
    }

    @Composable
    private fun createButton(
        defaultName: String,
        workingName: String?,
        onClick: KSuspendFunction0<String>,
        reset: Boolean = true
    ) {
        var text by remember { mutableStateOf(defaultName) }
        var enabled by remember { mutableStateOf(true) }
        var job: Job? = null

        Button(
            modifier = Modifier.width(itemWidth).height(itemHeight).padding(itemPadding),
            onClick = {
                if (job == null || job?.isActive == false) {
                    job = composableScope.launch {
                        workingName?.let { text = it }
                        text = onClick()
                        if (reset) {
                            enabled = false
                            delay(500)
                            text = defaultName
                            enabled = true
                        }
                        job?.cancel()
                    }
                } else if (workingName != null) {
                    composableScope.launch {
                        job?.cancelAndJoin()
                        text = "Cancelled..."
                        enabled = false
                        delay(1000)
                        text = defaultName
                        enabled = true
                    }
                }
            },
            enabled = enabled,
        ) {
            Text(text)
        }
    }


    @Composable
    private fun createDropdown(
        name: String,
        list: List<String>,
        defaultValue: String,
        onValueChanged: (value: String) -> Unit
    ) {
        var isListExpanded by remember { mutableStateOf(false) }
        var selectedValue by remember { mutableStateOf(defaultValue) }

        val icon = if (isListExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column {
            OutlinedTextField(
                modifier = Modifier.width(itemWidth).height(itemHeight).padding(itemPadding),
                readOnly = true,
                value = selectedValue,
                onValueChange = { selectedValue = it },
                label = { Text(name) },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { isListExpanded = !isListExpanded })
                }
            )

            DropdownMenu(
                modifier = Modifier.width(itemWidth).height(itemHeight * 3),
                expanded = isListExpanded,
                onDismissRequest = { isListExpanded = false },
            ) {
                list.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChanged(label)
                            selectedValue = label
                            isListExpanded = false
                        },
                        Modifier.width(itemWidth).height(itemHeight / 2)
                    ) {
                        Text(text = label, fontSize = 14.sp)
                    }
                }
            }
        }
    }

    @Composable
    private fun createInput(
        name: String,
        defaultValue: String,
        onValueChanged: (value: String) -> Unit
    ) {
        var selectedValue by remember { mutableStateOf(defaultValue) }

        Column {
            OutlinedTextField(
                modifier = Modifier.width(itemWidth).height(itemHeight).padding(itemPadding),
                value = selectedValue,
                onValueChange = { selectedValue = it; onValueChanged(it) },
                label = { Text(name) },
            )
        }
    }
}