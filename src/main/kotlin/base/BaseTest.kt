package base

import driver.AppFactory
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeSuite

open class BaseTest {

    @BeforeClass
    fun launchApp() {
        AppFactory.launchApp()
    }

    @AfterMethod
    fun closeApp(result: ITestResult) {
        if (result.status == ITestResult.FAILURE) {
            Util.getScreenshot(result.name)
        }
    }

    @BeforeSuite
    fun serverStart() {
        if (AppData.isCloud.contains("false")) {
            AppiumServer.start()
        }
    }

    @AfterSuite
    fun serverStop() {
        AppiumServer.stop()
    }
}
