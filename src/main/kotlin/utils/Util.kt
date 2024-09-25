package ui.util

import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.clipboard.ClipboardContentType
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.By
import org.openqa.selenium.Point
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.interactions.Sequence
import java.time.Duration

class ElementNotFoundException(message: String) : Exception(message)

object Util {

    private const val DEFAULT_RETRIES_COUNT = 3
    const val DEFAULT_SWIPE_COUNT = 5
    private const val DEFAULT_DELAY_BEFORE_ACTION = 1
    private const val DEFAULT_DELAY_AFTER_ACTION = 0
    private const val DEFAULT_DISTANCE = 0.1

    fun getCenterOfElement(element: WebElement?): Point {
        element?.let {
            val size = element.size
            val start = Point(element.location.x, element.location.y)
            val end = Point(element.location.x + size.width, element.location.y + size.height)
            return Point((start.x + end.x) / 2, (start.y + end.y) / 2)
        } ?: throw ElementNotFoundException("Element is null")
    }

    fun clickInCenterOfElement(driver: AppiumDriver, element: WebElement) {
        val input = PointerInput(PointerInput.Kind.TOUCH, "finger1")
        val middle = getCenterOfElement(element)
        val actions = Sequence(input, 0)
        actions.addAction(
            input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), middle.x, middle.y)
        )
        actions.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        actions.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
        driver.perform(listOf(actions))
    }

    fun clickByCoordinates(driver: AppiumDriver, point: Point): Boolean {
        val input = PointerInput(PointerInput.Kind.TOUCH, "finger1")
        val seq = Sequence(input, 0)
        seq.addAction(
            input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.x, point.y)
        )
        seq.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        seq.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
        driver.perform(listOf(seq))
        return true
    }

    fun waitAndClick(
        driver: AppiumDriver,
        element: WebElement?,
        delayBeforeClick: Int = DEFAULT_DELAY_BEFORE_ACTION,
        delayAfterClick: Int = DEFAULT_DELAY_AFTER_ACTION,
        withException: Boolean = true
    ): Boolean {
        var isClickSuccess = false
        try {
            element?.let {
                Thread.sleep(delayBeforeClick.toLong() * 1000)
                it.click()
                isClickSuccess = true
                Thread.sleep(delayAfterClick.toLong() * 1000)
            }
        } catch (ex: Exception) {
        }
        return if (!isClickSuccess && withException) {
            throw ElementNotFoundException("Cannot click on element")
        } else {
            isClickSuccess
        }
    }

    fun waitAndSendKeys(
        driver: AppiumDriver,
        element: WebElement?,
        text: String,
        delayBefore: Int = DEFAULT_DELAY_BEFORE_ACTION,
        delayAfter: Int = DEFAULT_DELAY_AFTER_ACTION,
        withException: Boolean = true
    ): Boolean {
        Thread.sleep(delayBefore.toLong() * 1000)
        element?.sendKeys(text)
        if (driver is AndroidDriver) driver.hideKeyboard()
        Thread.sleep(delayAfter.toLong() * 1000)

        return if (element == null) {
            if (withException) {
                throw ElementNotFoundException("Cannot send text, element is null")
            }
            false
        } else {
            element.isDisplayed
        }
    }

    fun swipe(driver: AppiumDriver, direction: SwipeDirection, distance: Double = DEFAULT_DISTANCE) {
        if (distance < 0 || distance > 1) error("Scroll distance must be between 0 and 1")

        val screenWidth = driver.manage().window().size.width
        val screenHeight = driver.manage().window().size.height
        val midPoint = Point((screenWidth * 0.5).toInt(), (screenHeight * 0.5).toInt())

        val pointsForSwipe = when (direction) {
            SwipeDirection.FINGER_TO_UP -> Pair(
                Point(midPoint.x, (midPoint.y + (screenHeight * distance)).toInt()),
                midPoint
            )

            SwipeDirection.FINGER_TO_DOWN -> Pair(
                midPoint,
                Point(midPoint.x, (midPoint.y + (screenHeight * distance)).toInt())
            )

            SwipeDirection.FINGER_TO_RIGHT -> Pair(
                Point(midPoint.x - (screenWidth * distance).toInt(), midPoint.y),
                midPoint
            )

            SwipeDirection.FINGER_TO_LEFT -> Pair(
                midPoint,
                Point(midPoint.x - (screenWidth * distance).toInt(), midPoint.y)
            )
        }

        swipeByPoints(driver, pointsForSwipe.first, pointsForSwipe.second)
    }

    fun swipeByPoints(driver: AppiumDriver, start: Point, end: Point, duration: Duration = Duration.ofMillis(1000)) {
        val input = PointerInput(PointerInput.Kind.TOUCH, "finger1")
        val swipe = Sequence(input, 0)
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y))
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y))
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
        driver.perform(listOf(swipe))
    }

    fun waitForVisibilityOfElementBy(
        driver: AppiumDriver,
        by: By,
        retriesCount: Int = DEFAULT_RETRIES_COUNT,
        withException: Boolean = true
    ): Boolean {
        var isVisible = false
        for (attempt in 0..retriesCount) {
            try {
                val element = driver.findElement(by)
                isVisible = element.isDisplayed
                if (isVisible) break
            } catch (ex: Exception) {
                Thread.sleep(1000)
            }
        }

        return if (!isVisible && withException) {
            throw ElementNotFoundException("Element is not visible")
        } else {
            isVisible
        }
    }

    fun waitUntilElementGone(
        driver: AppiumDriver,
        by: By,
        retriesCount: Int = DEFAULT_RETRIES_COUNT,
        withException: Boolean = false
    ): Boolean {
        var isDisplayed = true
        for (attempt in 0..retriesCount) {
            try {
                val element = driver.findElement(by)
                isDisplayed = element.isDisplayed
                if (!isDisplayed) break
            } catch (ex: Exception) {
                isDisplayed = false
                break
            }
            Thread.sleep(1000)
        }
        return if (withException && isDisplayed) {
            throw ElementNotFoundException("Element is still visible after retries")
        } else {
            !isDisplayed
        }
    }

    enum class SwipeDirection {
        FINGER_TO_UP, FINGER_TO_DOWN, FINGER_TO_RIGHT, FINGER_TO_LEFT
    }
}
