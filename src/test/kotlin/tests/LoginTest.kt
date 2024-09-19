package tests

import BaseTest
import org.testng.Assert.assertEquals
import org.testng.Assert.assertTrue
import org.testng.annotations.Test
import utils.DataProviders
import utils.StringHelper.ERROR_PASSWORD_REQUIRED
import utils.StringHelper.ERROR_USERNAME_REQUIRED
import utils.StringHelper.VALID_PASSWORD
import utils.StringHelper.VALID_USERNAME

class LoginTest : BaseTest() {

    @Test(priority = 1)
    fun emptyLoginAndCorrectPasswordTest() {
        menuPage.navigateToLogin()
        with(loginPage) {
            menuPage.navigateToLogin()
            enterUserEmail("")
            enterUserPassword(VALID_PASSWORD)
            tapLoginButton()
        }
        assertEquals(loginPage.getUserNameErrorText(), ERROR_USERNAME_REQUIRED)
    }

    @Test(priority = 2)
    fun correctLoginAndEmptyPasswordTest() {
        with(loginPage) {
            enterUserEmail(VALID_USERNAME)
            enterUserPassword("")
            tapLoginButton()
        }
        assertEquals(loginPage.getUserPasswordErrorText(), ERROR_PASSWORD_REQUIRED)
    }

    @Test(dataProvider = "invalid-login-dataProvider", dataProviderClass = DataProviders::class, priority = 3)
    fun invalidLoginTest(userName: String, password: String, errorText: String) {
        with(loginPage) {
            enterUserEmail(userName)
            enterUserPassword(password)
            tapLoginButton()
        }
        assertEquals(loginPage.getCredentialsErrorText(), errorText)
    }

    @Test(priority = 4)
    fun validLoginTest() {
        with(loginPage) {
            enterUserEmail(VALID_USERNAME)
            enterUserPassword(VALID_PASSWORD)
            tapLoginButton()
        }
        assertTrue(productsPage.getTitleMainPage())
    }
}
