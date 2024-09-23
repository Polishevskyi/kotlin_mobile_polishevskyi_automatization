package driver

import org.openqa.selenium.WebDriver

object AppDriver {

    private val driver: ThreadLocal<WebDriver> = ThreadLocal()

    fun getDriver(): WebDriver? {
        return driver.get()
    }

    fun getCurrentDriver(): WebDriver? {
        return getDriver()
    }

    fun setDriver(driverInstance: WebDriver) {
        driver.set(driverInstance)
    }
}
