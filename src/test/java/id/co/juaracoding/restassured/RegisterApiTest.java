package id.co.juaracoding.restassured;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import id.co.juaracoding.restassured.util.RsaHelper;
import id.co.juaracoding.util.TestConfig;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Pengujian otomatis endpoint Registrasi Mandiri (POST /api/v1/register) menggunakan RestAssured.
 */
public class RegisterApiTest extends BaseRestAssuredTest {

    private String buatSuffixUnikDelapanDigit(long offset) {
        return String.format("%08d", (System.currentTimeMillis() + offset) % 100000000L);
    }

    @Test
    public void should_return_200_and_magic_link_when_register_valid() {
        String unik = buatSuffixUnikDelapanDigit(0L);
        String[] captcha = ambilCaptcha();

        String username = "apiusr_" + unik;
        String emailPlain = "apiusr_" + unik + "@simpleapps.test";
        String phonePlain = "0812" + unik;
        String ktpPlain = "32010101" + unik;
        String npwpPlain = "09010101" + unik;
        String birthDatePlain = "2000-01-15";

        String requestBody = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt(TestConfig.REGISTER_DEFAULT_PASSWORD) + "\","
                + "\"full_name\":\"" + TestConfig.REGISTER_DEFAULT_FULL_NAME + "\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"" + TestConfig.REGISTER_DEFAULT_ADDRESS + "\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"MALE\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + RsaHelper.encrypt(ktpPlain) + "\","
                + "\"tax_id_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"postal_code\":\"10110\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/register");

        response.then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("code", equalTo(200))
                .body("data.magic_link", notNullValue());
    }

    @Test
    public void should_return_400_errors_when_id_card_number_bukan_16_digit() {
        String unik = buatSuffixUnikDelapanDigit(1L);
        String[] captcha = ambilCaptcha();

        String username = "apineg_" + unik;
        String emailPlain = "apineg_" + unik + "@simpleapps.test";
        String phonePlain = "0813" + unik;
        String npwpPlain = "09020202" + unik;
        String birthDatePlain = "2000-01-15";

        String requestBody = "{"
                + "\"username\":\"" + username + "\","
                + "\"password\":\"" + RsaHelper.encrypt(TestConfig.REGISTER_DEFAULT_PASSWORD) + "\","
                + "\"full_name\":\"" + TestConfig.REGISTER_DEFAULT_FULL_NAME + "\","
                + "\"email\":\"" + RsaHelper.encrypt(emailPlain) + "\","
                + "\"phone_number\":\"" + RsaHelper.encrypt(phonePlain) + "\","
                + "\"address\":\"" + TestConfig.REGISTER_DEFAULT_ADDRESS + "\","
                + "\"birth_date\":\"" + RsaHelper.encrypt(birthDatePlain) + "\","
                + "\"gender\":\"MALE\","
                + "\"last_education\":\"S1\","
                + "\"blood_type\":\"O\","
                + "\"id_card_number\":\"" + RsaHelper.encrypt(TestConfig.INVALID_ID_CARD_NUMBER) + "\","
                + "\"tax_id_number\":\"" + RsaHelper.encrypt(npwpPlain) + "\","
                + "\"postal_code\":\"10110\","
                + "\"captcha_answer\":\"" + captcha[1] + "\","
                + "\"captcha_hash\":\"" + captcha[0] + "\""
                + "}";

        Response response = specDasar()
                .body(requestBody)
                .post("/api/v1/register");

        response.then()
                .statusCode(400)
                .body("status", equalTo("ERROR"))
                .body("code", equalTo(400))
                .body("error_code", equalTo(TestConfig.ERROR_CODE_REGISTER_UMBRELLA))
                .body("errors.field", hasItem("id_card_number"))
                .body(
                        "errors.find { it.field == 'id_card_number' }.error_code",
                        equalTo(TestConfig.ERROR_CODE_FIELD_ID_CARD_NUMBER)
                );
    }
}

