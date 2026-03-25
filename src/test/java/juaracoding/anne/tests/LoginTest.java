package juaracoding.anne.tests;

import juaracoding.anne.BaseTest;
import juaracoding.anne.pages.InventoryPage;
import juaracoding.anne.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void loginPositive_validCredentials_redirectToInventory() {
        LoginPage loginPage = new LoginPage(driver, wait);
        InventoryPage inventoryPage = loginPage.loginValid(usernameValid, passwordValid);

        Assert.assertTrue(inventoryPage.isInventoryLoaded(), "Inventory harus tampil setelah login sukses.");
    }

    @Test
    public void loginNegative_invalidCredentials_showError() {
        LoginPage loginPage = new LoginPage(driver, wait);
        String actual = loginPage.loginInvalid("invalid_user", "invalid_pass");

        Assert.assertEquals(
                actual,
                "Epic sadface: Username and password do not match any user in this service",
                "Pesan error harus sesuai untuk kredensial invalid."
        );
    }

    @Test
    public void loginNegative_emptyCredentials_showError() {
        LoginPage loginPage = new LoginPage(driver, wait);
        String actual = loginPage.loginInvalid("", "");

        Assert.assertEquals(
                actual,
                "Epic sadface: Username is required",
                "Pesan error harus muncul saat username/password kosong."
        );
    }
}

