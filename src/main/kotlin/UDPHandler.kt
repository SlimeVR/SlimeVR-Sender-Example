import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.network.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/**
 * Handles the networking
 */
class UDPHandler {
    private val packetBuilder = PacketBuilder()

    // Can be any non-bound port. https://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers
    private val socket: BoundDatagramSocket =
        aSocket(SelectorManager(Dispatchers.IO)).udp().bind(InetSocketAddress("0.0.0.0", 21200))

    private val slimevrPort = 6969
    private val broadcastIp = "255.255.255.255"
    private var slimevrIp = broadcastIp

    /**
     * Sends a heartbeat to the server as to not time out.
     */
    suspend fun heartbeat() {
        while (true) {
            if (slimevrIp != broadcastIp)
                sendPacket(packetBuilder.heartbeatPacket)

            delay(800) // At least 1 time per second (<1000ms)
        }
    }

    /**
     * Returns the IP of the SlimeVR server after discovering it.
     */
    suspend fun handshake(imuType: IMUType, boardType: BoardType, mcuType: MCUType): String {
        // Reset IP to broadcast
        slimevrIp = broadcastIp

        var result = ""
        while (result == "") {
            // Send the SlimeVR Handshake
            sendPacket(packetBuilder.buildHandshakePacket(imuType, boardType, mcuType))

            // Listen for a UDP response
            CoroutineScope(coroutineContext).launch { result = listenForHandshake() }

            // Wait a bit and broadcast again
            delay(500)
        }

        return "Found server:\n$result"
    }

    /**
     * Adds another IMU
     */
    suspend fun addIMU(imuType: IMUType): String {
        if (slimevrIp == broadcastIp) return "Server not found"

        // Add an IMU
        sendPacket(packetBuilder.buildImuPacket(imuType))

        return "Added IMU"
    }

    /**
     * Rotate IMU to the Quaternion's values
     */
    suspend fun rotateIMU(imuID: Int, rotation: Quaternion): String {
        if (slimevrIp == broadcastIp) return "Server not found"

        // Rotate IMU
        sendPacket(packetBuilder.buildRotationPacket(imuID, rotation))

        return "Rotated IMU $imuID"
    }

    /**
     * Sends the passed packet to the stored ip and port
     */
    private suspend fun sendPacket(packet: ByteArray) {
        socket.send(
            Datagram(
                buildPacket { writeFully(packet) },
                InetSocketAddress(slimevrIp, slimevrPort)
            )
        )
    }

    /**
     * Listens for a handshake response
     */
    private suspend fun listenForHandshake(): String {
        while (true) {
            val received = socket.receive()

            val receivedMessage = try {
                received.packet.readText(Charsets.UTF_8)
            } catch (e: CharacterCodingException) {
                ""
            }

            if (receivedMessage.contains("Hey OVR =D 5")) {
                slimevrIp = received.address.toJavaAddress().address
                return slimevrIp
            }
        }
    }
}
