package id.co.juaracoding.selenium;

import id.co.juaracoding.selenium.pages.RegisterPage;
import id.co.juaracoding.util.TestConfig;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Pengujian otomatis alur Registrasi Mandiri pada aplikasi Simple Apps.
 */
public class RegistrasiTest extends BaseSeleniumTest {

    @Test
    public void registrasi_dengan_data_valid_berhasil_mengarahkan_ke_cek_email() {
        bukaHalaman("/register");

        String unik = String.format("%08d", System.currentTimeMillis() % 100000000L);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.isiUsername("user_" + unik);
        registerPage.isiPassword(TestConfig.REGISTER_DEFAULT_PASSWORD);
        registerPage.isiNamaLengkap(TestConfig.REGISTER_DEFAULT_FULL_NAME);
        registerPage.isiEmail("user_" + unik + "@simpleapps.test");
        registerPage.isiNomorHp("0812" + unik);
        registerPage.isiAlamat(TestConfig.REGISTER_DEFAULT_ADDRESS);
        registerPage.pilihTanggalLahir(
                TestConfig.REGISTER_BIRTH_YEAR,
                TestConfig.REGISTER_BIRTH_MONTH,
                TestConfig.REGISTER_BIRTH_DAY
        );
        registerPage.pilihJenisKelamin(TestConfig.REGISTER_DEFAULT_GENDER);
        registerPage.isiNomorKtp("32010101" + unik);
        registerPage.isiNomorNpwp("09010101" + unik);
        registerPage.isiCaptchaOtomatis();
        registerPage.klikTombolDaftar();

        Assert.assertTrue(
                registerPage.isHalamanCekEmailTampil(),
                "Halaman Cek Email harus tampil setelah registrasi berhasil"
        );
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/check-email"),
                "URL harus berpindah ke /check-email setelah form registrasi valid dikirim"
        );
    }

    @Test
    public void registrasi_dengan_nomor_ktp_bukan_16_digit_ditolak_form() {
        bukaHalaman("/register");

        String unik = String.format("%08d", (System.currentTimeMillis() + 1L) % 100000000L);

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.isiUsername("neg_" + unik);
        registerPage.isiPassword(TestConfig.REGISTER_DEFAULT_PASSWORD);
        registerPage.isiNamaLengkap(TestConfig.REGISTER_DEFAULT_FULL_NAME);
        registerPage.isiEmail("neg_" + unik + "@simpleapps.test");
        registerPage.isiNomorHp("0813" + unik);
        registerPage.isiAlamat(TestConfig.REGISTER_DEFAULT_ADDRESS);
        registerPage.pilihTanggalLahir(
                TestConfig.REGISTER_BIRTH_YEAR,
                TestConfig.REGISTER_BIRTH_MONTH,
                TestConfig.REGISTER_BIRTH_DAY
        );
        registerPage.pilihJenisKelamin(TestConfig.REGISTER_DEFAULT_GENDER);
        registerPage.isiNomorKtp(TestConfig.INVALID_ID_CARD_NUMBER);
        registerPage.isiNomorNpwp("09020202" + unik);
        registerPage.isiCaptchaOtomatis();

        Assert.assertTrue(
                registerPage.isPesanErrorValidasiFieldTampil(),
                "Pesan kesalahan validasi harus muncul saat nomor KTP tidak berjumlah 16 digit"
        );
        Assert.assertTrue(
                registerPage.ambilTeksErrorValidasiField().contains("16 digit"),
                "Teks pesan kesalahan harus menjelaskan bahwa nomor KTP wajib 16 digit angka"
        );
        Assert.assertFalse(
                registerPage.isTombolDaftarAktif(),
                "Tombol Daftar harus dinonaktifkan (ditolak form) selama nomor KTP tidak valid"
        );
        Assert.assertTrue(
                driver.getCurrentUrl().contains("/register"),
                "Pengguna harus tetap berada di halaman /register saat validasi gagal"
        );
    }
}

