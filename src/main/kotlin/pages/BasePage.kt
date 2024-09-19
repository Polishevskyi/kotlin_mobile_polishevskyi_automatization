package pages

import driver.AppDriver
import io.appium.java_client.pagefactory.AppiumFieldDecorator
import org.openqa.selenium.support.PageFactory

open class BasePage {

    init {
        PageFactory.initElements(AppiumFieldDecorator(AppDriver.getCurrentDriver()), this)
    }
}
