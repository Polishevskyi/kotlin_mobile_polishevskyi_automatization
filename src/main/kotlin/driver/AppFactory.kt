package driver

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.ios.IOSDriver
import io.appium.java_client.ios.options.XCUITestOptions
import org.testng.SkipException
import java.net.URL

object AppFactory {

    private var driver: AppiumDriver? = null
    private val browserstackOptions: MutableMap<String, Any> = mutableMapOf()

    private fun getBrowserstackOptions(): Map<String, Any> {
        return browserstackOptions.apply {
            put("userName", "serhiitestovkiy_fyRQuV")
            put("accessKey", "UUpKxrixq9XqPp7mhzqe")
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
                setApp("bs://d47dee8664b8ec67348d554b8dde75c28d870d50")
                setCapability("bstack:options", getBrowserstackOptions())
                driver = AndroidDriver(URL("http://hub-cloud.browserstack.com/wd/hub/"), this)
            }
        }
        driver?.let { AppDriver.setDriver(it) }
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
                setApp("bs://24b0d4c64ff227ceb4bad47b8c2018c3d893947a")
                setCapability("bstack:options", getBrowserstackOptions())
                driver = IOSDriver(URL("http://hub-cloud.browserstack.com/wd/hub/"), this)
            }
        }
        driver?.let { AppDriver.setDriver(it) }
    }

    fun launchApp() {
        when {
            AppData.platform.contains("ios") -> iosLaunchApp()
            AppData.platform.contains("android") -> androidLaunchApp()
            else -> throw SkipException("Enter valid platform value, android/ios")
        }
    }
}