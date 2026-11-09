package id.co.juaracoding.selenium.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object untuk halaman Registrasi Akun (/register) pada aplikasi Simple Apps.
 */
public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.cssSelector("[data-testid='register-username-input']");
    private final By passwordInput = By.cssSelector("[data-testid='register-password-input'] input");
    private final By fullNameInput = By.cssSelector("[data-testid='register-full-name-input']");
    private final By emailInput = By.cssSelector("[data-testid='register-email-input']");
    private final By phoneNumberInput = By.cssSelector("[data-testid='register-phone-number-input']");
    private final By addressInput = By.cssSelector("[data-testid='register-address-input']");
    private final By birthDateInput = By.cssSelector("[data-testid='register-birth-date-input'] input");
    private final By datepickerSelectYearButton = By.cssSelector(".p-datepicker-select-year");
    private final By genderSelect = By.cssSelector("[data-testid='register-gender-input']");
    private final By idCardNumberInput = By.cssSelector("[data-testid='register-id-card-number-input']");
    private final By taxIdNumberInput = By.cssSelector("[data-testid='register-tax-id-number-input']");
    private final By captchaValueHint = By.cssSelector("[data-testid='register-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='register-captcha-input']");
    private final By registerSubmitButton = By.cssSelector("[data-testid='register-submit-button']");
    private final By fieldValidationError = By.cssSelector("small.error");
    private final By checkEmailMessage = By.cssSelector("[data-testid='check-email-message']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void isiUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        element.clear();
        element.sendKeys(username);
    }

    public void isiPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        element.clear();
        element.sendKeys(password);
    }

    public void isiNamaLengkap(String namaLengkap) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput));
        element.clear();
        element.sendKeys(namaLengkap);
    }

    public void isiEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        element.clear();
        element.sendKeys(email);
    }

    public void isiNomorHp(String nomorHp) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneNumberInput));
        element.clear();
        element.sendKeys(nomorHp);
    }

    public void isiAlamat(String alamat) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput));
        element.clear();
        element.sendKeys(alamat);
    }

    public void pilihTanggalLahir(String tahun, String bulanSingkat, String tanggal) {
        wait.until(ExpectedConditions.elementToBeClickable(birthDateInput)).click();
        wait.until(ExpectedConditions.elementToBeClickable(datepickerSelectYearButton)).click();

        By yearOption = By.xpath(
                "//span[contains(@class,'p-datepicker-year') and normalize-space(text())='" + tahun + "']"
        );
        wait.until(ExpectedConditions.elementToBeClickable(yearOption)).click();

        By monthOption = By.xpath(
                "//span[contains(@class,'p-datepicker-month') and normalize-space(text())='" + bulanSingkat + "']"
        );
        wait.until(ExpectedConditions.elementToBeClickable(monthOption)).click();

        By dayCell = By.xpath("//td[@aria-label='" + tanggal + "']");
        wait.until(ExpectedConditions.elementToBeClickable(dayCell)).click();
    }

    public void pilihJenisKelamin(String labelJenisKelamin) {
        wait.until(ExpectedConditions.elementToBeClickable(genderSelect)).click();
        By option = By.xpath("//ul[@id='reg-gender_list']//li[@aria-label='" + labelJenisKelamin + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void isiNomorKtp(String nomorKtp) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(idCardNumberInput));
        element.clear();
        element.sendKeys(nomorKtp);
    }

    public void isiNomorNpwp(String nomorNpwp) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(taxIdNumberInput));
        element.clear();
        element.sendKeys(nomorNpwp);
    }

    public String bacaNilaiCaptchaTesting() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(captchaValueHint))
                .getText()
                .trim();
    }

    public void isiCaptchaOtomatis() {
        String nilaiCaptcha = bacaNilaiCaptchaTesting();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaInput));
        element.clear();
        element.sendKeys(nilaiCaptcha);
    }

    public void klikTombolDaftar() {
        wait.until(ExpectedConditions.elementToBeClickable(registerSubmitButton)).click();
    }

    public boolean isTombolDaftarAktif() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(registerSubmitButton)).isEnabled();
    }

    public boolean isPesanErrorValidasiFieldTampil() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(fieldValidationError)).isDisplayed();
    }

    public String ambilTeksErrorValidasiField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(fieldValidationError))
                .getText()
                .trim();
    }

    public boolean isHalamanCekEmailTampil() {
        wait.until(ExpectedConditions.urlContains("/check-email"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checkEmailMessage)).isDisplayed();
    }
}

