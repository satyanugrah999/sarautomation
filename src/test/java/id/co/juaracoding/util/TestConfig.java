package id.co.juaracoding.util;

/**
 * Konfigurasi terpusat untuk pengujian otomatisasi Selenium pada aplikasi Simple Apps.
 * Seluruh nilai URL, lisensi, dan kredensial pengujian wajib diambil dari kelas ini.
 */
public final class TestConfig {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String LICENSE_KEY = "d461265dd7323fef9755bb3257275d67";

    // Kredensial akun seed yang valid (README §4 / §5)
    public static final String VALID_USERNAME = "admin1";
    public static final String VALID_PASSWORD = "Admin1@123";
    public static final String VALID_USER_FULL_NAME = "Admin Satu";
    public static final String REGISTERED_EMAIL = "admin1@simpleapps.test";

    // Nilai pengujian skenario negatif
    public static final String INVALID_PASSWORD = "PasswordSalah123!";
    public static final String UNREGISTERED_EMAIL = "tidakterdaftar@simpleapps.test";
    public static final String INVALID_CAPTCHA = "00000";
    public static final String INVALID_ID_CARD_NUMBER = "12345678";

    // Nilai default form registrasi
    public static final String REGISTER_DEFAULT_PASSWORD = "Password1@123";
    public static final String REGISTER_DEFAULT_FULL_NAME = "Satya Penguji Otomatis";
    public static final String REGISTER_DEFAULT_ADDRESS = "Jl. Merdeka Raya No. 10 Jakarta Pusat";
    public static final String REGISTER_DEFAULT_GENDER = "Pria";
    public static final String REGISTER_BIRTH_YEAR = "2020";
    public static final String REGISTER_BIRTH_MONTH = "Jan";
    public static final String REGISTER_BIRTH_DAY = "15";

    private TestConfig() {
        // Mencegah instansiasi kelas konfigurasi
    }
}
