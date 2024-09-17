package tests

import base.BaseTest
import base.StringHelper.ERROR_INVALID_CREDENTIALS
import base.StringHelper.ERROR_PASSWORD_REQUIRED
import base.StringHelper.ERROR_USERNAME_REQUIRED
import base.StringHelper.VALID_PASSWORD
import base.StringHelper.VALID_USERNAME
import org.testng.Assert
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import pages.LoginPage
import pages.MenuPage
import pages.ProductsPage

class LoginTest : BaseTest() {

    private lateinit var menuPage: MenuPage
    private lateinit var loginPage: LoginPage
    private lateinit var productsPage: ProductsPage

    @BeforeClass
    fun setup() {
        menuPage = MenuPage()
        loginPage = LoginPage()
        productsPage = ProductsPage()
        menuPage.navigateToLogin()
    }

    @Test(priority = 4)
    fun validLoginTest() {
        loginPage.login(VALID_USERNAME, VALID_PASSWORD)
        Assert.assertTrue(productsPage.waitForProductText())
    }

    @Test(priority = 1)
    fun invalidLoginEmptyUserNameTest() {
        loginPage.login("", VALID_PASSWORD)
        Assert.assertEquals(loginPage.getUserNameErrorText(), ERROR_USERNAME_REQUIRED)
    }

    @Test(priority = 2)
    fun invalidLoginEmptyPasswordTest() {
        loginPage.login(VALID_USERNAME, "")
        Assert.assertEquals(loginPage.getPasswordErrorText(), ERROR_PASSWORD_REQUIRED)
    }

    @Test(dataProvider = "invalid-login-dataProvider", priority = 3)
    fun invalidLoginTest(uName: String, password: String, errorText: String) {
        loginPage.login(uName, password)
        Assert.assertEquals(loginPage.getCredentialsErrorText(), errorText)
    }

    @DataProvider(name = "invalid-login-dataProvider")
    fun dataProviderArr(): Array<Array<Any>> {
        return arrayOf(
            arrayOf(VALID_USERNAME, "1234", ERROR_INVALID_CREDENTIALS),
            arrayOf("bob@example", "1234", ERROR_INVALID_CREDENTIALS)
        )
    }
}
