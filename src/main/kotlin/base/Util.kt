package base

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import driver.AppDriver
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.interactions.Sequence
import org.openqa.selenium.remote.RemoteWebElement
import java.io.File
import java.time.Duration
import java.util.*

object Util {
    private var SCROLL_RATIO = 0.5
    private var SCROLL_DUR: Duration = Duration.ofMillis(500)

    fun getItems(listItems: By): Set<WebElement> {
        var prevPageSource = ""
        val items: MutableSet<WebElement> = mutableSetOf()

        val driver = AppDriver.getCurrentDriver()

        if (driver is AndroidDriver) {
            while (!isEndOfPage(prevPageSource)) {
                prevPageSource = driver.pageSource
                items.addAll(driver.findElements(listItems))
                scroll(ScrollDirection.DOWN, SCROLL_RATIO)
                Thread.sleep(1000)
            }
            scrollToTop()
        } else if (driver is IOSDriver) {
            items.addAll(driver.findElements(listItems).toSet())
        }

        return items
    }

    private fun scrollToTop() {
        var prevPageSource = ""
        while (!isEndOfPage(prevPageSource)) {
            prevPageSource = AppDriver.getCurrentDriver()!!.pageSource
            Thread.sleep(100)
            scroll(ScrollDirection.UP, SCROLL_RATIO)
            Thread.sleep(500)
        }
    }

    fun scrollAndClick(listItems: By, attrName: String, text: String) {
        var prevPageSource = ""
        var found = false

        while (!isEndOfPage(prevPageSource) && !found) {
            prevPageSource = AppDriver.getCurrentDriver()!!.pageSource
            for (el in AppDriver.getCurrentDriver()!!.findElements(listItems)) {
                if (el.getAttribute(attrName).equals(text, ignoreCase = true)) {
                    el.click()
                    found = true
                    break
                }
            }
            if (!found) {
                scroll(ScrollDirection.DOWN, SCROLL_RATIO)
                Thread.sleep(1000)
            }
        }
    }


    fun scrollNclick(byEl: By) {
        var prevPageSource = ""
        while (!isEndOfPage(prevPageSource)) {
            prevPageSource = AppDriver.getCurrentDriver()!!.pageSource
            try {
                AppDriver.getCurrentDriver()!!.findElement(byEl).click()
            } catch (e: org.openqa.selenium.NoSuchElementException) { // Явно вказуємо простір імен
                scroll(ScrollDirection.DOWN, SCROLL_RATIO)
            }
        }
    }

    private fun isEndOfPage(pageSource: String): Boolean {
        return pageSource == AppDriver.getCurrentDriver()!!.pageSource
    }

    enum class ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    private fun scroll(dir: ScrollDirection, scrollRatio: Double) {
        if (scrollRatio < 0 || scrollRatio > 1) {
            throw Error("Scroll distance must be between 0 and 1")
        }
        val size = AppDriver.getCurrentDriver()!!.manage().window().size
        println(size)
        val midPoint = Point((size.width * 0.5).toInt(), (size.height * 0.5).toInt())
        val bottom = midPoint.y + (midPoint.y * scrollRatio).toInt()
        val top = midPoint.y - (midPoint.y * scrollRatio).toInt()
        val left = midPoint.x - (midPoint.x * scrollRatio).toInt()
        val right = midPoint.x + (midPoint.x * scrollRatio).toInt()
        when (dir) {
            ScrollDirection.UP -> swipe(Point(midPoint.x, top), Point(midPoint.x, bottom), SCROLL_DUR)
            ScrollDirection.DOWN -> swipe(Point(midPoint.x, bottom), Point(midPoint.x, top), SCROLL_DUR)
            ScrollDirection.LEFT -> swipe(Point(left, midPoint.y), Point(right, midPoint.y), SCROLL_DUR)
            ScrollDirection.RIGHT -> swipe(Point(right, midPoint.y), Point(left, midPoint.y), SCROLL_DUR)
        }
    }

    private fun swipe(start: Point, end: Point, duration: Duration) {
        val input = PointerInput(PointerInput.Kind.TOUCH, "finger1")
        val swipe = Sequence(input, 0)
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y))
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y))
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
        (AppDriver.getCurrentDriver() as AppiumDriver).perform(ImmutableList.of(swipe))
    }

    fun longPress(el: WebElement) {
        val location = el.location
        val input = PointerInput(PointerInput.Kind.TOUCH, "finger1")
        val swipe = Sequence(input, 0)
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), location.x, location.y))
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        swipe.addAction(
            input.createPointerMove(
                Duration.ofSeconds(1),
                PointerInput.Origin.viewport(),
                location.x,
                location.y
            )
        )
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
        (AppDriver.getCurrentDriver() as AppiumDriver).perform(ImmutableList.of(swipe))
    }

    fun longPressGesturePlugin(el: WebElement) {
        (AppDriver.getCurrentDriver() as JavascriptExecutor).executeScript(
            "gesture: longPress",
            ImmutableMap.of("elementId", (el as RemoteWebElement).id, "pressure", 0.5, "duration", 800)
        )
    }

    fun swipeGesturePlugin(el: WebElement, direction: String) {
        (AppDriver.getCurrentDriver() as JavascriptExecutor).executeScript(
            "gesture: swipe",
            ImmutableMap.of("elementId", (el as RemoteWebElement).id, "percentage", 50, "direction", direction)
        )
    }

    private fun getCenter(el: WebElement): Point {
        val location = el.location
        val size = el.size
        return Point(location.x + size.width / 2, location.y + size.height / 2)
    }

    fun dragNDrop(source: WebElement, target: WebElement) {
        val pSource = getCenter(source)
        val pTarget = getCenter(target)
        swipe(pSource, pTarget, SCROLL_DUR)
    }

    fun dragNDropGesture(source: WebElement, target: WebElement) {
        (AppDriver.getCurrentDriver() as JavascriptExecutor).executeScript(
            "gesture: dragAndDrop",
            ImmutableMap.of(
                "sourceId", (source as RemoteWebElement).id,
                "destinationId", (target as RemoteWebElement).id
            )
        )
    }

    fun drawing(drawPanel: WebElement) {
        val location = drawPanel.location
        val size = drawPanel.size
        val pSource = Point(location.x + size.width / 2, location.y + 10)
        val pDest = Point(location.x + size.width / 2, location.y + size.height - 10)
        swipe(pSource, pDest, SCROLL_DUR)
    }

    fun captureScreenShotOf(el: WebElement, fileName: String) {
        val newImg = el.getScreenshotAs(OutputType.FILE)
        try {
            FileUtils.copyFile(newImg, File("./screenshot/$fileName.jpg"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun captureFullPageShot(fileName: String) {
        val newImg = (AppDriver.getCurrentDriver() as FirefoxDriver).getFullPageScreenshotAs(OutputType.FILE)
        try {
            FileUtils.copyFile(newImg, File("./screenshot/$fileName.jpg"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getScreenshot(imageName: String): String {
        val ts = AppDriver.getCurrentDriver() as TakesScreenshot
        val f = ts.getScreenshotAs(OutputType.FILE)
        val filePath = "./screenshot/$imageName.jpg"
        FileUtils.copyFile(f, File(filePath))
        return filePath
    }

    fun convertImgBase64(screenshotPath: String): String {
        val file = FileUtils.readFileToByteArray(File(screenshotPath))
        return Base64.getEncoder().encodeToString(file)
    }
}
