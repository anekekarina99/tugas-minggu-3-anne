package juaracoding.anne.tests;

import juaracoding.anne.BaseTest;
import juaracoding.anne.pages.CartPage;
import juaracoding.anne.pages.CheckoutPage;
import juaracoding.anne.pages.InventoryPage;
import juaracoding.anne.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void loginAndAddToCart() {
        LoginPage loginPage = new LoginPage(driver, wait);
        inventoryPage = loginPage.loginValid(usernameValid, passwordValid);
        inventoryPage.clearCartIfNeeded();
        inventoryPage.addFirstItemToCart();
    }

    @Test
    public void checkoutPositive_validInformation_completeOrder() {
        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        String completeMessage = checkoutPage.submitInformationPositive("Anne", "Karina", "12345");
        Assert.assertEquals(completeMessage, "Thank you for your order!", "Checkout harus berhasil dan menampilkan pesan sukses.");
    }

    @Test
    public void checkoutNegative_emptyFirstName_showError() {
        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        String errorMessage = checkoutPage.submitInformationNegative("", "Karina", "12345");
        Assert.assertEquals(errorMessage, "Error: First Name is required", "Pesan error harus muncul saat First Name kosong.");
    }
    
    @Test
    public void checkoutNegative_emptyLastName_showError() {
        CartPage cartPage = inventoryPage.goToCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        String errorMessage = checkoutPage.submitInformationNegative("Anne", "", "12345");
        Assert.assertEquals(errorMessage, "Error: Last Name is required", "Pesan error harus muncul saat Last Name kosong.");
    }
}
