package pages

import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.WebElement

class CartPage : BasePage() {
    @AndroidFindBy(accessibility = "product label")
    @iOSXCUITFindBy(accessibility = "product label")
    private lateinit var productLabel: WebElement

    @AndroidFindBy(accessibility = "product price")
    @iOSXCUITFindBy(accessibility = "product price")
    private lateinit var productPrice: WebElement

    fun getCartItemNameText(): String {
        return productLabel.text
    }

    fun getCartItemPriceText(): String {
        return productPrice.text
    }
}