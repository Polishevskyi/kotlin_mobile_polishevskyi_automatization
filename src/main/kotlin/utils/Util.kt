package utils

import com.google.common.collect.ImmutableList
import driver.AppDriver.getDriver
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.interactions.PointerInput
import java.time.Duration


object Util {

    private var SCROLL_DUR: Duration = Duration.ofMillis(500)

    enum class ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    fun scroll(dir: ScrollDirection, scrollRatio: Double) {
        if (scrollRatio < 0 || scrollRatio > 1) {
            throw Error("Scroll distance must be between 0 and 1")
        }

        val size: Dimension = getDriver()!!.manage().window().size
        println(size)

        val midPoint = Point((size.width * 0.5).toInt(), (size.height * 0.5).toInt())
        val bottom = (midPoint.y + (midPoint.y * scrollRatio)).toInt()
        val top = (midPoint.y - (midPoint.y * scrollRatio)).toInt()
        val left = (midPoint.x - (midPoint.x * scrollRatio)).toInt()
        val right = (midPoint.x + (midPoint.x * scrollRatio)).toInt()

        when (dir) {
            ScrollDirection.UP -> swipe(Point(midPoint.x, top), Point(midPoint.x, bottom), SCROLL_DUR)
            ScrollDirection.DOWN -> swipe(Point(midPoint.x, bottom), Point(midPoint.x, top), SCROLL_DUR)
            ScrollDirection.LEFT -> swipe(Point(left, midPoint.y), Point(right, midPoint.y), SCROLL_DUR)
            ScrollDirection.RIGHT -> swipe(Point(right, midPoint.y), Point(left, midPoint.y), SCROLL_DUR)
        }
    }

    private fun swipe(start: Point, end: Point, duration: Duration?) {
        val input = PointerInput(PointerInput.Kind.TOUCH, "finger1")
        val swipe = org.openqa.selenium.interactions.Sequence(input, 0)
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y))
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y))
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()))
        (getDriver() as AppiumDriver).perform(ImmutableList.of(swipe))
    }
}
