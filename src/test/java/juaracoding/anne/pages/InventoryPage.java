package juaracoding.anne.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By inventoryContainer = By.id("inventory_container");
    private final By inventoryButtons = By.cssSelector("div.inventory_item button.btn_inventory");
    private final By cartBadge = By.cssSelector("a.shopping_cart_link span.shopping_cart_badge");

    // Simpan tombol yang ditambahkan agar saat remove kita klik produk yang sama.
    private String lastAddedDataTest = null;

    private final By cartLink = By.cssSelector("a.shopping_cart_link");
    private final By cartItems = By.cssSelector("div.cart_item");
    private final By cartRemoveButtons = By.cssSelector("button.cart_button");
    private final By continueShoppingButton = By.id("continue-shopping");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;

        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer));
    }

    public boolean isInventoryLoaded() {
        return driver.findElements(inventoryContainer).size() > 0;
    }

    public int getCartBadgeCount() {
        List<WebElement> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        WebElement badge = badges.get(0);
        // Pada kondisi cart kosong, elemen badge bisa masih ada tapi hidden.
        if (!badge.isDisplayed()) {
            return 0;
        }

        String text = badge.getText().trim();
        if (text.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // Jika badge berubah menjadi format lain/temporer.
            return 0;
        }
    }

    private WebElement findFirstButtonByText(String expectedText) {
        List<WebElement> buttons = driver.findElements(inventoryButtons);
        for (WebElement btn : buttons) {
            String text = btn.getText().trim();
            if (text.equals(expectedText)) {
                return btn;
            }
        }
        throw new AssertionError("Button dengan text '" + expectedText + "' tidak ditemukan");
    }

    public void addFirstItemToCart() {
        WebElement addButton = findFirstButtonByText("Add to cart");
        lastAddedDataTest = addButton.getAttribute("data-test");
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addButton.click();

        wait.until(d -> getCartBadgeCount() == 1);
    }

    public void removeFirstItemFromCart() {
        WebElement removeButton;
        if (lastAddedDataTest != null && lastAddedDataTest.startsWith("add-to-cart-")) {
            String removeDataTest = lastAddedDataTest.replace("add-to-cart-", "remove-");
            removeButton = driver.findElement(By.cssSelector("button[data-test='" + removeDataTest + "']"));
        } else {
            removeButton = findFirstButtonByText("Remove");
        }

        wait.until(ExpectedConditions.elementToBeClickable(removeButton));
        removeButton.click();

        // Badge bisa menghilang/berubah asinkron; tunggu sampai benar-benar kosong.
        wait.until(d -> getCartBadgeCount() == 0);
    }

    public boolean hasButtonText(String expectedText) {
        try {
            findFirstButtonByText(expectedText);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public void clearCartIfNeeded() {
        // Pastikan cart benar-benar kosong sebelum menjalankan skenario add/remove.
        if (getCartBadgeCount() == 0) {
            return;
        }

        driver.findElement(cartLink).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItems));

        while (!driver.findElements(cartItems).isEmpty()) {
            List<WebElement> removes = driver.findElements(cartRemoveButtons);
            if (removes.isEmpty()) {
                throw new AssertionError("Cart tidak kosong, tetapi tombol remove tidak ditemukan.");
            }

            WebElement remove = removes.get(0);
            wait.until(ExpectedConditions.elementToBeClickable(remove)).click();

            int currentCount = driver.findElements(cartItems).size();
            wait.until(d -> d.findElements(cartItems).size() < currentCount);
        }

        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer));
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        return new CartPage(driver, wait);
    }
}

