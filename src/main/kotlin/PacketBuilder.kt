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
    private val firmwareBuild = 1
    private var packetID = AtomicLong(1)
    private var addingTrackerId = AtomicInteger(1)

    val heartbeatPacket: ByteArray =
        ByteBuffer.allocate(28).apply {
            putInt(0)
        }.array()

    fun buildHandshakePacket(imuType: IMUType, boardType: BoardType, mcuType: MCUType, trackerPosition: Int, dataSupport: Int): ByteArray {
        return ByteBuffer.allocate(128).apply {
            putInt(3)                                   // packet 3 header
            putLong(packetID.getAndIncrement())         // packet counter
            putInt(boardType.id)                        // Board type
            putInt(imuType.id)                          // IMU type
            putInt(mcuType.id)                          // MCU type
            repeat(3) { putInt(0) }               // IMU info (unused)
            putInt(firmwareBuild)                       // Firmware build
            put(fwString.length.toByte())               // Length of fw string
            put(fwString.toByteArray(Charsets.UTF_8))   // fw string
            put(byteArrayOf(0x01, 0x02, 0x03, 0x04, 0x05, 0x06)) // MAC address
                putInt(trackerPosition)                              // TrackerPosition
                putInt(dataSupport)                                  // Data support
        }.array()
    }

    fun buildSensorInfoPacket(imuType: IMUType, trackerPosition: Int, dataSupport: Int): ByteArray {
        return ByteBuffer.allocate(128).apply {
            putInt(15)                           // packet 15 header
            putLong(packetID.getAndIncrement()) // packet counter
            put(addingTrackerId.getAndIncrement().toByte())// tracker id (shown as IMU Tracker #x in SlimeVR)
            put(0.toByte())                     // sensor status
            put(imuType.id.toByte())            // imu type
            putInt(trackerPosition)             // TrackerPosition
            putInt(dataSupport)                 // Data support
        }.array()
    }

    fun buildRotationPacket(trackerId: Int, rotation: Quaternion): ByteArray {
        return ByteBuffer.allocate(128).apply {
            putInt(17)                           // packet 17 header
            putLong(packetID.getAndIncrement()) // packet counter
            put(trackerId.toByte())                 // tracker id (shown as IMU Tracker #x in SlimeVR)
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
            putInt(24)                          // packet 24 header
            putLong(packetID.getAndIncrement()) // packet counter
            put(trackerId.toByte())                 // tracker id
            putFloat(flexData)            // flex data value
        }.array()
    }
}