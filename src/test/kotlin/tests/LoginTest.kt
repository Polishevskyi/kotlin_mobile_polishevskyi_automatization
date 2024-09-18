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
    fun invalidLoginEmptyUserNameTest() {
        menuPage.navigateToLogin()
        with(loginPage) {
            enterUserName("")
            enterPassword(VALID_PASSWORD)
            clickLoginButton()
        }
        assertEquals(loginPage.getUserNameErrorText(), ERROR_USERNAME_REQUIRED)
    }

    @Test(priority = 2)
    fun invalidLoginEmptyPasswordTest() {
        with(loginPage) {
            enterUserName(VALID_USERNAME)
            enterPassword("")
            clickLoginButton()
        }
        assertEquals(loginPage.getPasswordErrorText(), ERROR_PASSWORD_REQUIRED)
    }

    @Test(dataProvider = "invalid-login-dataProvider", dataProviderClass = DataProviders::class, priority = 3)
    fun invalidLoginTest(userName: String, password: String, errorText: String) {
        with(loginPage) {
            enterUserName(userName)
            enterPassword(password)
            clickLoginButton()
        }
        assertEquals(loginPage.getCredentialsErrorText(), errorText)
    }

    @Test(priority = 4)
    fun validLoginTest() {
        with(loginPage) {
            enterUserName(VALID_USERNAME)
            enterPassword(VALID_PASSWORD)
            clickLoginButton()
        }
        assertTrue(productsPage.waitForProductText())
    }
}
