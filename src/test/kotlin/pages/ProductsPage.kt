package pages

import driver.AppDriver
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.By

class ProductsPage : BasePage() {

    private val productPage: By = AppiumBy.accessibilityId("container header")
    private var itemNames: By? = null
    private var firstItemName: By? = null
    private var firstItemPrice: By? = null

    init {
        if (AppDriver.getCurrentDriver() is AndroidDriver) {
            itemNames = AppiumBy.xpath("//android.widget.TextView[@content-desc='store item text']")
            firstItemName = AppiumBy.xpath("(//android.widget.TextView[@content-desc='store item text'])[1]")
            firstItemPrice = AppiumBy.xpath("(//android.widget.TextView[@content-desc='store item price'])[1]")
        } else if (AppDriver.getCurrentDriver() is IOSDriver) {
            itemNames = AppiumBy.xpath("//XCUIElementTypeStaticText[@name='store item text']")
            firstItemName = AppiumBy.xpath("(//XCUIElementTypeStaticText[@name='store item text'])[1]")
            firstItemPrice = AppiumBy.xpath("(//XCUIElementTypeStaticText[@name='store item price'])[1]")
        }
    }

    fun waitForProductText(): Boolean {
        return waitForEl(productPage).isDisplayed
    }
}
