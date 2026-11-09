package id.co.juaracoding.restassured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Pengujian otomatis endpoint Login (POST /api/v1/login) menggunakan RestAssured.
 */
public class LoginApiTest extends BaseRestAssuredTest {

    @Test
    public void should_return_200_and_token_when_login_valid() {
        String[] captcha = ambilCaptcha();
        String encryptedPassword = RsaHelper.encrypt(TestConfig.VALID_PASSWORD);

        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.VALID_USERNAME,
                encryptedPassword,
                captcha[1],
                captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/login");

        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("code", equalTo(200))
                .body("data.token", notNullValue())
                .body("data.user.username", equalTo(TestConfig.VALID_USERNAME));
    }

    @Test
    public void should_return_401_API_ECMXS40107_when_password_salah() {
        String[] captcha = ambilCaptcha();
        String encryptedWrongPassword = RsaHelper.encrypt(TestConfig.INVALID_PASSWORD);

        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                TestConfig.VALID_USERNAME,
                encryptedWrongPassword,
                captcha[1],
                captcha[0]
        );

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/login");

        response.then()
                .statusCode(401)
                .body("status", equalTo("ERROR"))
                .body("code", equalTo(401))
                .body("error_code", equalTo(TestConfig.ERROR_CODE_LOGIN_INVALID_CREDENTIALS));
    }
}

