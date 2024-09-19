package tests

import BaseTest
import org.testng.annotations.Test
import utils.StringHelper.DIGIT_ONE
import kotlin.test.assertEquals

class CartTest : BaseTest() {

    @Test
    fun `Verify add product to cart`() {
        val firstItemName = productsPage.getFirstItemNameText()
        productsPage.tapOnFirstItem()

        goodsPage.tapOnAddToCartButton()

        val cartLabel = goodsPage.getLabelOnCartTabText()
        assertEquals(cartLabel, DIGIT_ONE, "Label on cart doesn't equal to $DIGIT_ONE")

        goodsPage.tapOnCartTabButton()

        val cartItemName = cartPage.getProductLabelText()
        assertEquals(cartItemName, firstItemName, "Product name in the cart doesn't match the expected one")
    }
}
