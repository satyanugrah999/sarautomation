package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.ForgotPasswordPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Pengujian otomatis alur Lupa Password pada aplikasi Simple Apps.
 */
public class LupaPasswordTest extends BaseSeleniumTest {

    @Test
    public void lupa_password_dengan_email_terdaftar_berhasil_memicu_reset_password() {
        bukaHalaman("/forgot-password");

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.kirimPermintaanResetDenganCaptchaOtomatis(TestConfig.REGISTERED_EMAIL);

        Assert.assertTrue(
                forgotPasswordPage.isHalamanCekEmailTampil(),
                "Halaman Cek Email harus tampil setelah submit email terdaftar"
        );
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/check-email"),
                "URL harus berpindah ke /check-email setelah permintaan reset password dikirim"
        );
        Assert.assertTrue(
                forgotPasswordPage.isMagicLinkResetPasswordTampil(),
                "Magic link reset password harus diterbitkan pada mode TESTING untuk email yang terdaftar"
        );
    }

    @Test
    public void lupa_password_dengan_captcha_salah_menampilkan_pesan_error() {
        bukaHalaman("/forgot-password");

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.kirimPermintaanResetDenganCaptchaManual(
                TestConfig.UNREGISTERED_EMAIL,
                TestConfig.INVALID_CAPTCHA
        );

        Assert.assertTrue(
                forgotPasswordPage.isPesanErrorTampil(),
                "Toast pesan kesalahan harus tampil saat permintaan lupa password tidak valid"
        );
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/forgot-password"),
                "Pengguna harus tetap berada di halaman /forgot-password saat validasi gagal"
        );
    }

    @Test
    public void lupa_password_dengan_email_tidak_terdaftar_tidak_menerbitkan_magic_link() {
        bukaHalaman("/forgot-password");

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.kirimPermintaanResetDenganCaptchaOtomatis(TestConfig.UNREGISTERED_EMAIL);

        Assert.assertTrue(
                forgotPasswordPage.isHalamanCekEmailTampil(),
                "Sesuai FSD-WEB §5.3 (anti user-enumeration), halaman Cek Email tetap ditampilkan"
        );
        Assert.assertFalse(
                forgotPasswordPage.isMagicLinkResetPasswordTampil(),
                "Sistem tidak boleh menerbitkan magic link reset password untuk email yang tidak terdaftar"
        );
    }
}

