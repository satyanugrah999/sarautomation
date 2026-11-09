package id.co.juaracoding.util;

/**
 * Konfigurasi terpusat untuk pengujian otomatisasi Selenium pada aplikasi Simple Apps.
 * Seluruh nilai URL, lisensi, dan kredensial pengujian wajib diambil dari kelas ini.
 */
public final class TestConfig {

    public static final String BASE_URL = "http://localhost:8080";
    public static final String LICENSE_KEY = "d461265dd7323fef9755bb3257275d67";

    // Konfigurasi RestAssured API (Praktikum 3)
    public static final String BASE_API_URL = BASE_URL;
    public static final String X_API_KEY = LICENSE_KEY;
    public static final int DEFAULT_JWT_DURATION_MINUTES = 60;

    // Kode error API sesuai kontrak beku (API-CONTRACT.docx §3 & AutErrorCode)
    public static final String ERROR_CODE_LOGIN_INVALID_CREDENTIALS = "API-ECMXS40107";
    public static final String ERROR_CODE_REGISTER_UMBRELLA = "API-ECM010001";
    public static final String ERROR_CODE_FIELD_ID_CARD_NUMBER = "API-ECM010009";
    public static final String ERROR_CODE_CAPTCHA_INVALID = "API-ECM010022";

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
