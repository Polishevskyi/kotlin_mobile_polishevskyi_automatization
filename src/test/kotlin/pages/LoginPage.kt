package pages

import driver.AppDriver
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class LoginPage : BasePage() {

    private var userName: By? = null
    private var userNameErrorText: By? = null
    private var passwordErrorText: By? = null
    private var credentialsErrorText: By? = null

    init {
        if (AppDriver.getCurrentDriver() is AndroidDriver) {
            userName = AppiumBy.accessibilityId("Username input field")
            userNameErrorText =
                By.xpath("//android.view.ViewGroup[@content-desc='Username-error-message']/android.widget.TextView")
            passwordErrorText =
                By.xpath("//android.view.ViewGroup[@content-desc='Password-error-message']/android.widget.TextView")
            credentialsErrorText =
                By.xpath("//android.view.ViewGroup[@content-desc='generic-error-message']/android.widget.TextView")
        } else if (AppDriver.getCurrentDriver() is IOSDriver) {
            userName = AppiumBy.accessibilityId("Username input field")
            userNameErrorText =
                By.xpath("//XCUIElementTypeOther[@name='Username-error-message']/XCUIElementTypeStaticText")
            passwordErrorText =
                By.xpath("//XCUIElementTypeOther[@name='Password-error-message']/XCUIElementTypeStaticText")
            credentialsErrorText =
                By.xpath("//XCUIElementTypeOther[@name='generic-error-message']/XCUIElementTypeStaticText")
        }
    }

    @AndroidFindBy(accessibility = "Password input field")
    @iOSXCUITFindBy(accessibility = "Password input field")
    private lateinit var password: WebElement

    @AndroidFindBy(accessibility = "Login button")
    @iOSXCUITFindBy(accessibility = "Login button")
    private lateinit var btnLogin: WebElement

    private val menuPage = MenuPage()

    fun login(username: String, passwordText: String) {
        waitNtype(userName!!, username)
        password.clear()
        password.sendKeys(passwordText)
        btnLogin.click()
    }

    fun getUserNameErrorText(): String {
        return getText(userNameErrorText!!)
    }

    fun getPasswordErrorText(): String {
        return getText(passwordErrorText!!)
    }

    fun getCredentialsErrorText(): String {
        return getText(credentialsErrorText!!)
    }
}
