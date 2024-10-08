package pages

import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.WebElement

class GoodsPage : BasePage() {
    @AndroidFindBy(accessibility = "Add To Cart button")
    @iOSXCUITFindBy(accessibility = "Add To Cart button")
    private lateinit var addToCartButton: WebElement

    @AndroidFindBy(xpath = "(//android.widget.TextView[@text='1'])[1]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='tab bar option cart' and @label='1']")
    private lateinit var labelOnCartTab: WebElement

    @AndroidFindBy(accessibility = "product price")
    @iOSXCUITFindBy(accessibility = "product price")
    private lateinit var priceOnCartTab: WebElement

    @AndroidFindBy(accessibility = "cart badge")
    @iOSXCUITFindBy(accessibility = "tab bar option cart")
    private lateinit var cartTab: WebElement

    fun tapOnAddToCartBtn() {
        addToCartButton.click()
    }

    fun getCartNameText(): String {
        return labelOnCartTab.text
    }

    fun getCartPriceText(): String {
        return priceOnCartTab.text
    }

    fun tapOnCartTabButton() {
        cartTab.click()
    }
}
