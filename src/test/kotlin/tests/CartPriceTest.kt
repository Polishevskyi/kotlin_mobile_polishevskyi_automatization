package tests

import BaseTest
import org.testng.annotations.Test
import utils.StringHelper.ITEM_PRICE
import kotlin.test.assertEquals

class CartPriceTest : BaseTest() {

    @Test
    fun `Verify product price when add product to cart`() {
        val firstItemPrice = productsPage.getFirstItemPriceText()
        productsPage.tapOnFirstItem()

        goodsPage.tapOnAddToCartButton()

        val cartPrice = goodsPage.getPriceOnCartTabText()
        assertEquals(cartPrice, ITEM_PRICE, "Price is not equal to $ITEM_PRICE")

        goodsPage.tapOnCartTabButton()

        val cartItemPrice = cartPage.getProductPriceText()
        assertEquals(cartItemPrice, firstItemPrice, "Price doesn't equal the expected one")
    }
}