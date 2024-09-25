package driver

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.ios.options.XCUITestOptions
import org.testng.SkipException
import java.io.FileInputStream
import java.net.URL
import java.util.*

object AppFactory {

    private lateinit var driver: AppiumDriver
    private val browserstackOptions: MutableMap<String, Any> = mutableMapOf()

    private fun getBrowserstackOptions(): Map<String, Any> {
        return browserstackOptions.apply {
            put("userName", properties.getProperty("browserstack.userName"))
            put("accessKey", properties.getProperty("browserstack.accessKey"))
            put("appiumVersion", "2.4.1")
        }
    }

    private fun androidLaunchApp() {
        val options = UiAutomator2Options().apply {
            if (AppData.isCloud.contains("false")) {
                setDeviceName("Pixel 6 Pro API 34")
                setPlatformVersion("14.0")
                setAppPackage("com.saucelabs.mydemoapp.rn")
                setAppActivity(".MainActivity")
                driver = AndroidDriver(URL("http://127.0.0.1:4723/"), this)
            } else {
                setDeviceName("Google Pixel 6 Pro")
                setPlatformVersion("13.0")
                setAppPackage("com.saucelabs.mydemoapp.rn")
                setAppActivity(".MainActivity")
                setApp(properties.getProperty("browserstack.androidApp"))
                setCapability("bstack:options", getBrowserstackOptions())
                driver = AndroidDriver(URL("http://hub-cloud.browserstack.com/wd/hub/"), this)
            }
        }
        driver.let { AppDriver.setDriver(it) }
    }

    private fun iosLaunchApp() {
        val options = XCUITestOptions().apply {
            if (AppData.isCloud.contains("false")) {
                setDeviceName("iPhone 15 Pro")
                setPlatformVersion("17.2")
                setBundleId("com.saucelabs.mydemoapp.rn")
                driver = IOSDriver(URL("http://127.0.0.1:4723/"), this)
            } else {
                setDeviceName("iPhone 15 Pro")
                setPlatformVersion("17.2")
                setBundleId("com.saucelabs.mydemoapp.rn")
                setApp(properties.getProperty("browserstack.iosApp"))
                setCapability("bstack:options", getBrowserstackOptions())
                driver = IOSDriver(URL("http://hub-cloud.browserstack.com/wd/hub/"), this)
            }
        }
        driver.let { AppDriver.setDriver(it) }
    }

    fun launchApp() {
        when {
            AppData.platform.contains("ios") -> iosLaunchApp()
            AppData.platform.contains("android") -> androidLaunchApp()
            else -> throw SkipException("Enter valid platform value, android/ios")
        }
    }

    fun closeApp() {
        AppDriver.getCurrentDriver().quit()
    }

    private val properties: Properties by lazy {
        val props = Properties()
        FileInputStream("keystore.properties").use { props.load(it) }
        props
    }
}