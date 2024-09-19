package tests

import BaseTest
import org.testng.annotations.Test
import utils.StringHelper.DIGIT_ONE
import kotlin.test.assertEquals

class CartNameTest : BaseTest() {

    @Test
    fun `Verify product name when add product to cart`() {
        val firstItemName = productsPage.getFirstItemNameText()
        productsPage.tapOnFirstItem()

        goodsPage.tapOnAddToCartButton()

        val cartName = goodsPage.getNameOnCartTabText()
        assertEquals(cartName, DIGIT_ONE, "Name is not equal to $DIGIT_ONE")

        goodsPage.tapOnCartTabButton()

        val cartItemName = cartPage.getProductLabelText()
        assertEquals(cartItemName, firstItemName, "Name doesn't equal the expected one")
    }
}
