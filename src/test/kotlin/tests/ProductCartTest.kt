package tests

import BaseTest
import io.qameta.allure.Owner
import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import utils.StringHelper.DIGIT_ONE
import utils.StringHelper.ITEM_PRICE

class ProductCartTest : BaseTest() {

    @Test
    @Owner("Polishevskyi")
    fun `Verify product name and price when add product to cart`() {
        val firstItemName = productsPage.getFirstItemNameText()
        val firstItemPrice = productsPage.getFirstItemPriceText()

        productsPage.tapOnFirstItem()

        with(goodsPage) {
            tapOnAddToCartButton()
            val cartName = getNameOnCartTabText()
            assertEquals(cartName, DIGIT_ONE, "Name is not equal to $DIGIT_ONE")

            val cartPrice = getPriceOnCartTabText()
            assertEquals(cartPrice, ITEM_PRICE, "Price is not equal to $ITEM_PRICE")

            tapOnCartTabButton()
        }

        with(cartPage) {
            val cartItemName = getProductLabelText()
            assertEquals(cartItemName, firstItemName, "Name doesn't equal the expected one")

            val cartItemPrice = getProductPriceText()
            assertEquals(cartItemPrice, firstItemPrice, "Price doesn't equal the expected one")
        }
    }
}