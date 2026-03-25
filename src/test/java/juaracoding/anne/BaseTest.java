package juaracoding.anne;

import io.github.bonigarcia.wdm.WebDriverManager;
import juaracoding.anne.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected final String baseUrl = System.getProperty("baseUrl", "https://www.saucedemo.com");
    protected final String usernameValid = System.getProperty("usernameValid", "standard_user");
    protected final String passwordValid = System.getProperty("passwordValid", "secret_sauce");

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            // Chrome 109+ supports --headless=new; fallback is fine too.
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void loginIfNeeded(String username, String password) {
        // Convenience method; tests yang butuh halaman inventory biasanya pakai LoginPage secara langsung.
        new LoginPage(driver, wait).loginValid(username, password);
    }
}

