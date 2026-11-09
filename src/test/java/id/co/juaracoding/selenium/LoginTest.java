package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.LoginPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Pengujian otomatis alur Login pada aplikasi Simple Apps.
 */
public class LoginTest extends BaseSeleniumTest {

    @Test
    public void login_dengan_kredensial_valid_berhasil_masuk_ke_dashboard() {
        bukaHalaman("/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginDenganCaptchaOtomatis(TestConfig.VALID_USERNAME, TestConfig.VALID_PASSWORD);

        Assert.assertTrue(
                loginPage.isDashboardTampilSetelahLogin(),
                "Halaman dashboard beserta sidebar dan info pengguna harus tampil setelah login berhasil"
        );
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/dashboard"),
                "URL harus berpindah ke /dashboard setelah login berhasil"
        );
        Assert.assertTrue(
                loginPage.ambilTeksInfoPenggunaNavbar().contains(TestConfig.VALID_USER_FULL_NAME),
                "Navbar harus menampilkan nama lengkap akun seed yang login"
        );
    }

    @Test
    public void login_dengan_password_salah_menampilkan_pesan_error() {
        bukaHalaman("/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginDenganCaptchaOtomatis(TestConfig.VALID_USERNAME, TestConfig.INVALID_PASSWORD);

        Assert.assertTrue(
                loginPage.isPesanErrorLoginTampil(),
                "Toast pesan kesalahan harus muncul saat login menggunakan password yang salah"
        );
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Pengguna harus tetap berada di halaman /login saat autentikasi gagal"
        );
    }
}

