package pages

import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.WebElement

class LoginPage : BasePage() {

    @AndroidFindBy(accessibility = "Username input field")
    @iOSXCUITFindBy(accessibility = "Username input field")
    private lateinit var userName: WebElement

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Username-error-message']/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='Username-error-message']/XCUIElementTypeStaticText")
    private lateinit var userNameErrorText: WebElement

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Password-error-message']/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='Password-error-message']/XCUIElementTypeStaticText")
    private lateinit var passwordErrorText: WebElement

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='generic-error-message']/android.widget.TextView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='generic-error-message']/XCUIElementTypeStaticText")
    private lateinit var credentialsErrorText: WebElement

    @AndroidFindBy(accessibility = "Password input field")
    @iOSXCUITFindBy(accessibility = "Password input field")
    private lateinit var password: WebElement

    @AndroidFindBy(accessibility = "Login button")
    @iOSXCUITFindBy(accessibility = "Login button")
    private lateinit var btnLogin: WebElement

    fun enterUserEmail(username: String) {
        userName.clear()
        userName.sendKeys(username)
    }

    fun enterUserPassword(passwordText: String) {
        password.clear()
        password.sendKeys(passwordText)
    }

    fun tapOnLoginBtn() {
        btnLogin.click()
    }

    fun getUserNameErrorText(): String {
        return userNameErrorText.text
    }

    fun getUserPasswordErrorText(): String {
        return passwordErrorText.text
    }

    fun getCredentialsErrorText(): String {
        return credentialsErrorText.text
    }
}
