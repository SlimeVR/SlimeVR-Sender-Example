import io.ktor.utils.io.core.*
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * Handles building the packets.
 * List of packets: https://github.com/SlimeVR/SlimeVR-Server/blob/main/server/core/src/main/java/dev/slimevr/tracking/trackers/udp/UDPPacket.kt
 */
class PacketBuilder {
    private val fwString = "SlimeVR Sender Example"
    private val protocolVersion = 19 // First version with dataType support
    private var packetID = AtomicLong(1)
    private var addingTrackerId = AtomicInteger(0)

    val heartbeatPacket: ByteArray =
        ByteBuffer.allocate(28).apply {
            putInt(0)
        }.array()

    fun buildHandshakePacket(boardType: BoardType, mcuType: MCUType): ByteArray {
        addingTrackerId.set(0)
        return ByteBuffer.allocate(128).apply {
            putInt(3)                           // packet 3 header
            putLong(0)                          // packet counter
            putInt(boardType.id)                      // Board type
            putInt(0)                           // IMU type
            putInt(mcuType.id)                        // MCU type
            repeat(3) { putInt(0) }       // IMU info (unused)
            putInt(protocolVersion)                   // Protocol version
            put(fwString.length.toByte())             // Length of fw string
            put(fwString.toByteArray(Charsets.UTF_8)) // fw string
            put(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)) // MAC address
        }.array()
    }

    fun buildSensorInfoPacket(imuType: IMUType, trackerPosition: TrackerPosition?, dataType: TrackerDataType): ByteArray {
        return ByteBuffer.allocate(128).apply {
            putInt(15)                         // packet 15 header
            putLong(packetID.getAndIncrement())      // packet counter
            put(addingTrackerId.getAndIncrement().toByte()) // tracker id (shown as IMU Tracker #x in SlimeVR)
            put(0.toByte())                          // sensor status
            put(imuType.id.toByte())                 // imu type
            putShort(0)                        // Mag support
            put((trackerPosition?.id ?: 0).toByte()) // TrackerPosition
            put(dataType.id.toByte())                // Data type
        }.array()
    }

    fun buildRotationPacket(trackerId: Int, rotation: Quaternion): ByteArray {
        return ByteBuffer.allocate(128).apply {
            putInt(17)                    // packet 17 header
            putLong(packetID.getAndIncrement()) // packet counter
            put(trackerId.toByte())             // tracker id (shown as IMU Tracker #x in SlimeVR)
            put(1.toByte())                     // data type
            putFloat(rotation.x)                // Quaternion x
            putFloat(rotation.y)                // Quaternion y
            putFloat(rotation.z)                // Quaternion z
            putFloat(rotation.w)                // Quaternion w
            put(0.toByte())                     // Calibration info
        }.array()
    }

    fun buildFlexDataPacket(trackerId: Int, flexData: Float): ByteArray {
        return ByteBuffer.allocate(128).apply {
            putInt(26)                     // packet 24 header
            putLong(packetID.getAndIncrement()) // packet counter
            put(trackerId.toByte())             // tracker id
            putFloat(flexData)                  // flex data value
        }.array()
    }
}