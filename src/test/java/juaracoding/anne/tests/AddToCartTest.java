package juaracoding.anne.tests;

import juaracoding.anne.BaseTest;
import juaracoding.anne.pages.InventoryPage;
import juaracoding.anne.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginToInventory() {
        LoginPage loginPage = new LoginPage(driver, wait);
        inventoryPage = loginPage.loginValid(usernameValid, passwordValid);
        inventoryPage.clearCartIfNeeded();
    }

    @Test
    public void addToCartPositive_TC_INV_003_badge1_and_buttonRemove() {
        inventoryPage.addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1, "Cart badge harus bernilai 1.");
        Assert.assertTrue(inventoryPage.hasButtonText("Remove"), "Tombol pertama harus berubah menjadi 'Remove'.");
    }

    @Test
    public void addToCartNegative_removeItem_badge0_and_buttonAddToCart() {
        inventoryPage.addFirstItemToCart();
        inventoryPage.removeFirstItemFromCart();

        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 0, "Cart badge harus kembali bernilai 0.");
        Assert.assertTrue(inventoryPage.hasButtonText("Add to cart"), "Tombol pertama harus kembali menjadi 'Add to cart'.");
    }
}

