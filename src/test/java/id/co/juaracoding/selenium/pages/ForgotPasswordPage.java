package id.co.juaracoding.selenium.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object untuk halaman Lupa Password (/forgot-password) pada aplikasi Simple Apps.
 */
public class ForgotPasswordPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.cssSelector("[data-testid='forgot-password-email-input']");
    private final By captchaValueHint = By.cssSelector("[data-testid='forgot-password-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='forgot-password-captcha-input']");
    private final By submitButton = By.cssSelector("[data-testid='forgot-password-submit-button']");
    private final By errorToastIcon = By.cssSelector("[data-testid='toast-icon-error']");
    private final By checkEmailMessage = By.cssSelector("[data-testid='check-email-message']");
    private final By checkEmailMagicLink = By.cssSelector("[data-testid='check-email-magic-link']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void isiEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        element.clear();
        element.sendKeys(email);
    }

    public String bacaNilaiCaptchaTesting() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(captchaValueHint))
                .getText()
                .trim();
    }

    public void isiCaptcha(String nilaiCaptcha) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(captchaInput));
        element.clear();
        element.sendKeys(nilaiCaptcha);
    }

    public void isiCaptchaOtomatis() {
        String nilaiCaptcha = bacaNilaiCaptchaTesting();
        isiCaptcha(nilaiCaptcha);
    }

    public void klikTombolKirim() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void kirimPermintaanResetDenganCaptchaOtomatis(String email) {
        isiEmail(email);
        isiCaptchaOtomatis();
        klikTombolKirim();
    }

    public void kirimPermintaanResetDenganCaptchaManual(String email, String nilaiCaptcha) {
        isiEmail(email);
        isiCaptcha(nilaiCaptcha);
        klikTombolKirim();
    }

    public boolean isPesanErrorTampil() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastIcon)).isDisplayed();
    }

    public boolean isHalamanCekEmailTampil() {
        wait.until(ExpectedConditions.urlContains("/check-email"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(checkEmailMessage)).isDisplayed();
    }

    public boolean isMagicLinkResetPasswordTampil() {
        wait.until(ExpectedConditions.urlContains("/check-email"));
        return !driver.findElements(checkEmailMagicLink).isEmpty()
                && driver.findElement(checkEmailMagicLink).isDisplayed();
    }
}

