package pages

import io.appium.java_client.pagefactory.AndroidFindBy
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import org.openqa.selenium.WebElement

class ProductsPage : BasePage() {

    @AndroidFindBy(accessibility = "container header")
    @iOSXCUITFindBy(accessibility = "container header")
    private lateinit var productPage: WebElement

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='store item text'])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='store item text'])[1]")
    private lateinit var firstItemName: WebElement

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='store item price'])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='store item price'])[1]")
    private lateinit var firstItemPrice: WebElement

    @AndroidFindBy(accessibility = "sort button")
    @iOSXCUITFindBy(accessibility = "sort button")
    private lateinit var sortIcon: WebElement

    @AndroidFindBy(accessibility = "nameAsc")
    @iOSXCUITFindBy(accessibility = "nameAsc")
    private lateinit var sortByNameAscending: WebElement

    @AndroidFindBy(accessibility = "nameDesc")
    @iOSXCUITFindBy(accessibility = "nameDesc")
    private lateinit var sortByNameDescending: WebElement

    @AndroidFindBy(accessibility = "priceAsc")
    @iOSXCUITFindBy(accessibility = "priceAsc")
    private lateinit var sortByPriceAscending: WebElement

    @AndroidFindBy(accessibility = "priceDesc")
    @iOSXCUITFindBy(accessibility = "priceDesc")
    private lateinit var sortByPriceDescending: WebElement

    fun getTitleMainPage(): Boolean =
        productPage.let { runCatching { it.isDisplayed }.getOrDefault(false) }

    fun getFirstItemNameText(): String {
        return firstItemName.text
    }

    fun getFirstItemPriceText(): String {
        return firstItemPrice.text
    }

    fun tapOnFirstItem() {
        firstItemName.click()
    }

    fun tapOnSortIcon() {
        sortIcon.click()
    }

    fun tapOnNameAsc() {
        sortByNameAscending.click()
    }

    fun tapOnNameDesc() {
        sortByNameDescending.click()
    }

    fun tapOnPriceAsc() {
        sortByPriceAscending.click()
    }

    fun tapOnPriceDesc() {
        sortByPriceDescending.click()
    }
}
