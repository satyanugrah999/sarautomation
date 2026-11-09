package id.co.juaracoding.selenium.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object untuk halaman Login (/login) pada aplikasi Simple Apps.
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.cssSelector("[data-testid='login-username-input']");
    private final By passwordInput = By.cssSelector("[data-testid='login-password-input'] input");
    private final By captchaValueHint = By.cssSelector("[data-testid='login-captcha-value']");
    private final By captchaInput = By.cssSelector("[data-testid='login-captcha-input']");
    private final By loginSubmitButton = By.cssSelector("[data-testid='login-submit-button']");
    private final By errorToastIcon = By.cssSelector("[data-testid='toast-icon-error']");
    private final By navbarUserInfo = By.cssSelector("[data-testid='navbar-user-info']");
    private final By appSidebar = By.cssSelector("[data-testid='app-sidebar']");

    public LoginPage(WebDriver driver) {
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

    public void klikTombolLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginSubmitButton)).click();
    }

    public void loginDenganCaptchaOtomatis(String username, String password) {
        isiUsername(username);
        isiPassword(password);
        isiCaptchaOtomatis();
        klikTombolLogin();
    }

    public boolean isPesanErrorLoginTampil() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorToastIcon)).isDisplayed();
    }

    public boolean isDashboardTampilSetelahLogin() {
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        WebElement sidebar = wait.until(ExpectedConditions.visibilityOfElementLocated(appSidebar));
        WebElement infoUser = wait.until(ExpectedConditions.visibilityOfElementLocated(navbarUserInfo));
        return sidebar.isDisplayed() && infoUser.isDisplayed();
    }

    public String ambilTeksInfoPenggunaNavbar() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(navbarUserInfo))
                .getText()
                .trim();
    }
}

