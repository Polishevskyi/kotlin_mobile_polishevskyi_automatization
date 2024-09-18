package pages

import driver.AppDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import io.appium.java_client.remote.SupportsContextSwitching
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

open class BasePage {

    init {
        PageFactory.initElements(AppiumFieldDecorator(AppDriver.getCurrentDriver()), this)
    }

    private val wait = WebDriverWait(AppDriver.getCurrentDriver(), Duration.ofSeconds(30))

    protected fun waitForEl(byLocator: By): WebElement {
        return wait.until(ExpectedConditions.presenceOfElementLocated(byLocator))
    }

    protected fun getEl(byLocator: By): WebElement {
        return AppDriver.getCurrentDriver()!!.findElement(byLocator)
    }

    protected fun getEls(byLocator: By): List<WebElement> {
        return AppDriver.getCurrentDriver()!!.findElements(byLocator)
    }

    protected fun click(byLocator: By) {
        getEl(byLocator).click()
    }

    protected fun type(byLocator: By, text: String) {
        getEl(byLocator).sendKeys(text)
    }

    protected fun waitNtype(byLocator: By, text: String) {
        waitForEl(byLocator)
        getEl(byLocator).clear()
        getEl(byLocator).sendKeys(text)
    }

    protected fun waitNclick(byLocator: By) {
        waitForEl(byLocator).click()
    }

    protected fun size(byLocator: By): Int {
        return getEls(byLocator).size
    }

    protected fun size(els: List<WebElement>): Int {
        return els.size
    }

    protected fun getText(byLocator: By): String {
        return if (AppDriver.getCurrentDriver() is AndroidDriver) {
            getEl(byLocator).text
        } else if (AppDriver.getCurrentDriver() is IOSDriver) {
            getAttribute(byLocator, "value")
        } else {
            ""
        }
    }

    protected fun getAttribute(byLocator: By, attr: String): String {
        return waitForEl(byLocator).getAttribute(attr)
    }

    protected fun isListItemsContain(items: List<WebElement>, text: String): Boolean {
        return items.any { it.text.contains(text) }
    }

    protected fun isTextMatches(el: WebElement, text: String): Boolean {
        return el.text.equals(text, ignoreCase = true)
    }

    protected fun isTextContains(el: WebElement, text: String): Boolean {
        return el.text.contains(text)
    }

    protected fun getContexts(): Set<String> {
        return (AppDriver.getCurrentDriver() as SupportsContextSwitching).contextHandles
    }

//    protected fun getCurrentContext(): String {
//        return (AppDriver.getCurrentDriver() as SupportsContextSwitching).context
//    }
//
//    private fun getDropDownElement(byLocator: By): Select {
//        return Select(AppDriver.getCurrentDriver().findElement(byLocator))
//    }

    private fun getDropDownElement(el: WebElement): Select {
        return Select(el)
    }

    fun selectDropDownByOption(el: WebElement, option: String) {
        getDropDownElement(el).selectByVisibleText(option)
    }

    protected fun getDropDownOptions(el: WebElement): List<WebElement> {
        return getDropDownElement(el).options
    }

    protected fun getDropDownAllSelectedOptions(el: WebElement): List<WebElement> {
        return getDropDownElement(el).allSelectedOptions
    }

    protected fun getDropDownSelectedOption(el: WebElement): WebElement {
        return getDropDownElement(el).firstSelectedOption
    }

    protected fun isDropDownItemsContain(element: WebElement, text: String): Boolean {
        return getDropDownElement(element).options.any { it.text.contains(text) }
    }
}
