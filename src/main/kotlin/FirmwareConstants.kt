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

enum class TrackerPosition(val id: Int) {
    NONE(0),
    HEAD(1),
    NECK(2),
    UPPER_CHEST(3),
    CHEST(4),
    WAIST(5),
    HIP(6),
    LEFT_UPPER_LEG(7),
    RIGHT_UPPER_LEG(8),
    LEFT_LOWER_LEG(9),
    RIGHT_LOWER_LEG(10),
    LEFT_FOOT(11),
    RIGHT_FOOT(12),
    LEFT_LOWER_AR(13),
    RIGHT_LOWER_AR(14),
    LEFT_UPPER_ARM(15),
    RIGHT_UPPER_ARM(16),
    LEFT_HAND(17),
    RIGHT_HAND(18),
    LEFT_SHOULDER(19),
    RIGHT_SHOULDER(20),
    LEFT_THUMB_PROXIMAL(21),
    LEFT_THUMB_INTERMEDIATE(22),
    LEFT_THUMB_DISTAL(23),
    LEFT_INDEX_PROXIMAL(24),
    LEFT_INDEX_INTERMEDIATE(25),
    LEFT_INDEX_DISTAL(26),
    LEFT_MIDDLE_PROXIMAL(27),
    LEFT_MIDDLE_INTERMEDIATE(28),
    LEFT_MIDDLE_DISTAL(29),
    LEFT_RING_PROXIMAL(30),
    LEFT_RING_INTERMEDIATE(31),
    LEFT_RING_DISTAL(32),
    LEFT_LITTLE_PROXIMAL(33),
    LEFT_LITTLE_INTERMEDIATE(34),
    LEFT_LITTLE_DISTAL(35),
    RIGHT_THUMB_PROXIMAL(36),
    RIGHT_THUMB_INTERMEDIATE(37),
    RIGHT_THUMB_DISTAL(38),
    RIGHT_INDEX_PROXIMAL(39),
    RIGHT_INDEX_INTERMEDIATE(40),
    RIGHT_INDEX_DISTAL(41),
    RIGHT_MIDDLE_PROXIMAL(42),
    RIGHT_MIDDLE_INTERMEDIATE(43),
    RIGHT_MIDDLE_DISTAL(44),
    RIGHT_RING_PROXIMAL(45),
    RIGHT_RING_INTERMEDIATE(46),
    RIGHT_RING_DISTAL(47),
    RIGHT_LITTLE_PROXIMAL(48),
    RIGHT_LITTLE_INTERMEDIATE(49),
    RIGHT_LITTLE_DISTAL(50),
    ;

    companion object {
        fun getList(): List<String> {
            return entries.map {
                it.toString()
            }
        }
    }
}

enum class DataSupport(val id: Int) {
    ROTATION(0),
    FLEX_RESISTANCE(1),
    FLEX_ANGLE(2),
    ;

    companion object {
        fun getList(): List<String> {
            return entries.map {
                it.toString()
            }
        }
    }
}