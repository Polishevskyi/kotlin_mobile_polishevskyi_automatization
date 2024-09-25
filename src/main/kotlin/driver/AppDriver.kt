package driver

import io.appium.java_client.AppiumDriver

object AppDriver {

    private val driver: ThreadLocal<AppiumDriver> = ThreadLocal()

    fun getDriver(): AppiumDriver {
        return driver.get()
    }

    fun getCurrentDriver(): AppiumDriver {
        return getDriver()
    }

    fun setDriver(driverInstance: AppiumDriver) {
        driver.set(driverInstance)
    }
}
