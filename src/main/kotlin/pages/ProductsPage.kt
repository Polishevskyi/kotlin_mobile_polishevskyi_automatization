package pages

import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.WebElement

class ProductsPage : BasePage() {

    @AndroidFindBy(accessibility = "container header")
    @iOSXCUITFindBy(accessibility = "container header")
    private lateinit var productPage: WebElement

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='store item text']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='store item text']")
    private lateinit var itemNames: WebElement

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='store item text'])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='store item text'])[1]")
    private lateinit var firstItemName: WebElement

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='store item price'])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='store item price'])[1]")
    private lateinit var firstItemPrice: WebElement

    fun getTitleMainPage(): Boolean =
        productPage.let { runCatching { it.isDisplayed }.getOrDefault(false) }
}
