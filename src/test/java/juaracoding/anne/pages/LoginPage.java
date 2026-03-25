package juaracoding.anne.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");

    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    private void click(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        el.click();
    }

    public InventoryPage loginValid(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);

        // Saat login sukses, inventory_container muncul.
        By inventoryContainer = By.id("inventory_container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer));
        return new InventoryPage(driver, wait);
    }

    public String loginInvalid(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);

        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText().trim();
    }
}

