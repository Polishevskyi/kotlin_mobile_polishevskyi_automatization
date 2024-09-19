import driver.AppData
import driver.AppFactory
import driver.AppiumServer
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeSuite
import pages.LoginPage
import pages.MenuPage
import pages.ProductsPage
import utils.DataGenerator
import utils.Util

open class BaseTest {

    protected val dataGenerator : DataGenerator by lazy { DataGenerator() }
    protected val menuPage: MenuPage by lazy { MenuPage() }
    protected val loginPage: LoginPage by lazy { LoginPage() }
    protected val productsPage: ProductsPage by lazy { ProductsPage() }

    @BeforeSuite
    fun serverStart() {
        if (AppData.isCloud.contains("false")) {
            AppiumServer.start()
        }
    }

    @AfterSuite
    fun serverStop() {
        AppFactory.closeApp()
        AppiumServer.stop()
    }

    @BeforeClass
    open fun setupBase() {
        AppFactory.launchApp()
    }

    @AfterMethod
    fun closeApp(result: ITestResult) {
        if (result.status == ITestResult.FAILURE) {
            Util.getScreenshot(result.name)
        }
    }
}