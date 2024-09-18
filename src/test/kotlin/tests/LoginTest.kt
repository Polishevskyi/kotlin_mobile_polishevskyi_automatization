package tests

import BaseTest
import org.testng.Assert
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
        loginPage.enterUserName("")
        loginPage.enterPassword(VALID_PASSWORD)
        loginPage.clickLoginButton()
        Assert.assertEquals(loginPage.getUserNameErrorText(), ERROR_USERNAME_REQUIRED)
    }

    @Test(priority = 2)
    fun invalidLoginEmptyPasswordTest() {
        loginPage.enterUserName(VALID_USERNAME)
        loginPage.enterPassword("")
        loginPage.clickLoginButton()
        Assert.assertEquals(loginPage.getPasswordErrorText(), ERROR_PASSWORD_REQUIRED)
    }

    @Test(dataProvider = "invalid-login-dataProvider", dataProviderClass = DataProviders::class, priority = 3)
    fun invalidLoginTest(uName: String, password: String, errorText: String) {
        loginPage.enterUserName(uName)
        loginPage.enterPassword(password)
        loginPage.clickLoginButton()
        Assert.assertEquals(loginPage.getCredentialsErrorText(), errorText)
    }

    @Test(priority = 4)
    fun validLoginTest() {
        loginPage.enterUserName(VALID_USERNAME)
        loginPage.enterPassword(VALID_PASSWORD)
        loginPage.clickLoginButton()
        Assert.assertTrue(productsPage.waitForProductText())
    }
}
