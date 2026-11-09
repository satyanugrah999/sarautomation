package id.co.juaracoding.restassured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Pengujian otomatis endpoint Lupa Password (POST /api/v1/forgot-password) menggunakan RestAssured.
 * Seluruh field pada endpoint ini dikirim sebagai plaintext (tanpa enkripsi RSA).
 */
public class ForgotPasswordApiTest extends BaseRestAssuredTest {

    @Test
    public void should_return_200_and_magic_link_when_email_terdaftar() {
        String[] captcha = ambilCaptcha();

        String requestBody = String.format(
                "{\"email\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.REGISTERED_EMAIL,
                captcha[1],
                captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/forgot-password");

        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("code", equalTo(200))
                .body("data.magic_link", notNullValue());
    }

    @Test
    public void should_return_200_juga_when_email_tidak_terdaftar() {
        String[] captcha = ambilCaptcha();
        String emailTidakTerdaftar = "tidak-pernah-daftar-" + System.currentTimeMillis() + "@simpleapps.test";

        String requestBody = String.format(
                "{\"email\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                emailTidakTerdaftar,
                captcha[1],
                captcha[0]
        );

        // Sesuai FSD-WEB §5.3 & modul-RestAssured §4.4 (anti user-enumeration):
        // Server tetap membalas 200 SUCCESS tetapi data.magic_link bernilai null.
        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/forgot-password");

        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("code", equalTo(200))
                .body("data.magic_link", nullValue());
    }

    @Test
    public void should_return_400_API_ECM010022_when_captcha_salah() {
        String[] captcha = ambilCaptcha();

        String requestBody = String.format(
                "{\"email\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.UNREGISTERED_EMAIL,
                TestConfig.INVALID_CAPTCHA,
                captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/forgot-password");

        response.then()
                .statusCode(400)
                .body("status", equalTo("ERROR"))
                .body("code", equalTo(400))
                .body("error_code", equalTo(TestConfig.ERROR_CODE_CAPTCHA_INVALID));
    }
}

