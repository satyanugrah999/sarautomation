package id.co.juaracoding.selenium;

import id.co.juaracoding.util.TestConfig;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Kelas induk untuk seluruh pengujian Selenium Web UI pada aplikasi Simple Apps.
 * Mengelola siklus hidup WebDriver serta melewati gerbang aplikasi (/license dan /env).
 */
public abstract class BaseSeleniumTest {

    private static final By LICENSE_KEY_INPUT = By.cssSelector("[data-testid='license-key-input']");
    private static final By LICENSE_SUBMIT_BUTTON = By.cssSelector("[data-testid='license-submit-button']");
    private static final By ENV_SUBMIT_BUTTON = By.cssSelector("[data-testid='env-submit-button']");

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1366,900");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Membuka halaman tujuan pada aplikasi dan melewati gerbang lisensi (/license)
     * serta gerbang konfigurasi runtime (/env) secara otomatis apabila muncul.
     *
     * @param path jalur relatif halaman yang dituju (contoh: "/login", "/register", "/forgot-password")
     */
    protected void bukaHalaman(String path) {
        driver.get(TestConfig.BASE_URL + path);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/license"),
                ExpectedConditions.urlContains("/env"),
                ExpectedConditions.urlContains(path)
        ));

        if (driver.getCurrentUrl().contains("/license")) {
            WebElement inputLisensi = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(LICENSE_KEY_INPUT)
            );
            inputLisensi.clear();
            inputLisensi.sendKeys(TestConfig.LICENSE_KEY);

            wait.until(ExpectedConditions.elementToBeClickable(LICENSE_SUBMIT_BUTTON)).click();

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/env"),
                    ExpectedConditions.urlContains("/login")
            ));
        }

        if (driver.getCurrentUrl().contains("/env")) {
            wait.until(ExpectedConditions.elementToBeClickable(ENV_SUBMIT_BUTTON)).click();
            wait.until(ExpectedConditions.urlContains("/login"));
        }

        if (!driver.getCurrentUrl().contains(path)) {
            driver.get(TestConfig.BASE_URL + path);
            wait.until(ExpectedConditions.urlContains(path));
        }
    }
}

