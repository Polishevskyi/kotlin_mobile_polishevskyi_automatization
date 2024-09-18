package pages

import driver.AppDriver
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class MenuPage : BasePage() {

    private var menuOption: By? = null

    init {
        if (AppDriver.getCurrentDriver() is AndroidDriver) {
            menuOption = AppiumBy.accessibilityId("open menu")
        } else if (AppDriver.getCurrentDriver() is IOSDriver) {
            menuOption = AppiumBy.accessibilityId("tab bar option menu")
        }
    }

    @AndroidFindBy(accessibility = "menu item log in")
    @iOSXCUITFindBy(accessibility = "menu item log in")
    lateinit var menuLogin: WebElement

    fun navigateToLogin() {
        waitNclick(menuOption!!)
        menuLogin.click()
    }
}
