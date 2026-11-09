package id.co.juaracoding.restassured;

import static io.restassured.RestAssured.given;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

/**
 * Kelas dasar untuk seluruh pengujian REST API menggunakan RestAssured pada aplikasi Simple Apps.
 * Menyediakan spesifikasi request dasar (header X-API-KEY), pembuka gerbang runtime .env,
 * serta helper pengambilan captcha dan token autentikasi JWT.
 */
public abstract class BaseRestAssuredTest {

    @BeforeClass
    public void setUpBaseUri() {
        RestAssured.baseURI = TestConfig.BASE_API_URL;
        pastikanGerbangEnvTerbuka();
    }

    protected RequestSpecification specDasar() {
        return given()
                .header("X-API-KEY", TestConfig.X_API_KEY)
                .contentType("application/json");
    }

    protected RequestSpecification specDenganToken(String token) {
        return specDasar().header("Authorization", "Bearer " + token);
    }

    /**
     * Memastikan gerbang runtime .env (/api/v1/env) sudah terbuka pada sesi JVM backend saat ini
     * sehingga pemanggilan endpoint bisnis tidak tertahan oleh kode 401 API-ECMXS40108.
     */
    protected void pastikanGerbangEnvTerbuka() {
        Response statusResponse = specDasar().get("/api/v1/env/status");
        Boolean configured = statusResponse.jsonPath().getBoolean("data.configured");
        if (!Boolean.TRUE.equals(configured)) {
            String body = String.format(
                    "{\"jwt_duration_minutes\": %d}",
                    TestConfig.DEFAULT_JWT_DURATION_MINUTES
            );
            specDasar().body(body).post("/api/v1/env");
        }
    }

    /**
     * Mengambil pasangan captcha_hash (indeks 0) dan captcha_value (indeks 1) dari GET /api/v1/captcha.
     */
    protected String[] ambilCaptcha() {
        Response response = specDasar().get("/api/v1/captcha");
        String captchaHash = response.jsonPath().getString("data.captcha_hash");
        String captchaValue = response.jsonPath().getString("data.captcha_value");
        return new String[] { captchaHash, captchaValue };
    }

    /**
     * Melakukan login menggunakan kredensial yang valid dan mengembalikan token JWT.
     */
    protected String loginDapatkanToken(String username, String plainPassword) {
        String[] captcha = ambilCaptcha();
        String encryptedPassword = RsaHelper.encrypt(plainPassword);

        String body = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"captcha_answer\":\"%s\",\"captcha_hash\":\"%s\"}",
                username, encryptedPassword, captcha[1], captcha[0]
        );

        Response response = specDasar().body(body).post("/api/v1/login");
        String token = response.jsonPath().getString("data.token");
        if (token == null) {
            throw new IllegalStateException(
                    "Gagal memperoleh JWT untuk user " + username + ": " + response.asString()
            );
        }
        return token;
    }
}

