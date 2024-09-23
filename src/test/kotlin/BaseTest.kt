import driver.AppData
import driver.AppDriver
import driver.AppFactory
import driver.AppiumServer
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeSuite
import pages.*

open class BaseTest {

    protected val menuPage: MenuPage by lazy { MenuPage() }
    protected val loginPage: LoginPage by lazy { LoginPage() }
    protected val productsPage: ProductsPage by lazy { ProductsPage() }
    protected val goodsPage: GoodsPage by lazy { GoodsPage() }
    protected val cartPage: CartPage by lazy { CartPage() }

    @BeforeSuite
    fun serverStart() {
        if (AppData.isCloud.contains("false")) {
            AppiumServer.start()
        }
    }

    @AfterSuite
    fun serverStop() {
        AppFactory.closeApp()
        AppDriver.getCurrentDriver()?.quit()
    }

    @BeforeClass
    open fun setupBase() {
        AppFactory.launchApp()
    }
}