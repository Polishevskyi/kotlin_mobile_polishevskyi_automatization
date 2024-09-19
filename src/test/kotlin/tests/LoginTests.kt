package tests

import BaseTest
import io.qameta.allure.Owner
import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test
import utils.DataProviders
import utils.StringHelper.ERROR_PASSWORD_REQUIRED
import utils.StringHelper.ERROR_USERNAME_REQUIRED
import utils.StringHelper.VALID_PASSWORD
import utils.StringHelper.VALID_USERNAME

class LoginTests : BaseTest() {

    @Test(priority = 1)
    @Owner("Polishevskyi")
    fun `Verify error when username is empty and password is correct`() {
        with(menuPage) {
            tapOnMenuTab()
            tapOnLogInBtn()
        }
        with(loginPage) {
            enterUserEmail("")
            enterUserPassword(VALID_PASSWORD)
            tapLoginButton()
        }
        assertEquals(
            loginPage.getUserNameErrorText(),
            ERROR_USERNAME_REQUIRED,
            "Username error message should be displayed when username is empty"
        )
    }

    @Test(priority = 2)
    @Owner("Polishevskyi")
    fun `Verify error when username is correct and password is empty`() {
        with(loginPage) {
            enterUserEmail(VALID_USERNAME)
            enterUserPassword("")
            tapLoginButton()
        }
        assertEquals(
            loginPage.getUserPasswordErrorText(),
            ERROR_PASSWORD_REQUIRED,
            "Password error message should be displayed when password is empty"
        )
    }

    @Test(dataProvider = "invalid-login-dataProvider", dataProviderClass = DataProviders::class, priority = 3)
    @Owner("Polishevskyi")
    fun `Verify error when login credentials are invalid`(userName: String, password: String, errorText: String) {
        with(loginPage) {
            enterUserEmail(userName)
            enterUserPassword(password)
            tapLoginButton()
        }
        assertEquals(
            loginPage.getCredentialsErrorText(),
            errorText,
            "Credentials error message should match the expected text for invalid login"
        )
    }

    @Test(priority = 4)
    @Owner("Polishevskyi")
    fun `Verify successful login with valid credentials`() {
        with(loginPage) {
            enterUserEmail(VALID_USERNAME)
            enterUserPassword(VALID_PASSWORD)
            tapLoginButton()
        }
        assertTrue(productsPage.getTitleMainPage(), "Main page should be displayed after successful login")
    }
}
