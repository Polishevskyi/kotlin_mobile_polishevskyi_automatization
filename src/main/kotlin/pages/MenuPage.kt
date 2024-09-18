package pages

import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.WebElement

class MenuPage : BasePage() {

    @AndroidFindBy(accessibility = "open menu")
    @iOSXCUITFindBy(accessibility = "tab bar option menu")
    private lateinit var menuOption: WebElement

    @AndroidFindBy(accessibility = "menu item log in")
    @iOSXCUITFindBy(accessibility = "menu item log in")
    lateinit var menuLogin: WebElement

    fun navigateToLogin() {
        menuOption.click()
        menuLogin.click()
    }
}
