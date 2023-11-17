enum class IMUType(val id: Int) {
    UNKNOWN(0),
    MPU9250(1),
    MPU6500(2),
    BNO080(3),
    BNO085(4),
    BNO055(5),
    MPU6050(6),
    BNO086(7),
    BMI160(8),
    ICM20948(9),
    ;

    companion object {
        fun getList(): List<String> {
            return entries.map {
                it.toString()
            }
        }
    }
}

enum class BoardType(val id: Int) {
    UNKNOWN(0),
    SLIMEVR_LEGACY(1),
    SLIMEVR_DEV(2),
    NODEMCU(3),
    CUSTOM(4),
    WROOM32(5),
    WEMOSD1MINI(6),
    TTGO_TBASE(7),
    ESP01(8),
    SLIMEVR(9),
    LOLIN_C3_MINI(10),
    BEETLE32C32(11),
    ES32C3DEVKITM1(12),
    ;

    companion object {
        fun getList(): List<String> {
            return entries.map {
                it.toString()
            }
        }
    }
}

enum class MCUType(val id: Int) {
    UNKNOWN(0),
    ESP8266(1),
    ESP32(2),
    ;

    companion object {
        fun getList(): List<String> {
            return entries.map {
                it.toString()
            }
        }
    }
}
