package juaracoding.anne.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By checkoutButton = By.id("checkout");
    private By cartItem = By.className("cart_item");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_contents_container")));
    }

    public CheckoutPage proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutPage(driver, wait);
    }
    
    public int getCartItemCount() {
        return driver.findElements(cartItem).size();
    }
}
