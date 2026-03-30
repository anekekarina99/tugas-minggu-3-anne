package juaracoding.anne.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By zipCodeInput = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By finishButton = By.id("finish");
    private By errorMessage = By.cssSelector("h3[data-test='error']");
    private By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_info_container")));
    }

    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    public void fillInformation(String first, String last, String zip) {
        type(firstNameInput, first);
        type(lastNameInput, last);
        type(zipCodeInput, zip);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public String submitInformationNegative(String first, String last, String zip) {
        fillInformation(first, last, zip);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText().trim();
    }

    public String submitInformationPositive(String first, String last, String zip) {
        fillInformation(first, last, zip);
        
        // Wait until step two is loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout_summary_container")));
        
        // Click finish
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        
        // Wait for complete page
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText().trim();
    }
}
