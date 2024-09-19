package tests

import BaseTest
import io.qameta.allure.Owner
import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import utils.StringHelper.PRODUCT_NAME_ASCENDING
import utils.StringHelper.PRODUCT_NAME_DESCENDING
import utils.StringHelper.PRODUCT_PRICE_ASCENDING
import utils.StringHelper.PRODUCT_PRICE_DESCENDING

class SortTests : BaseTest() {

    @Test
    @Owner("Polishevskyi")
    fun `Verify product name when sorted by name ascending`() {
        with(productsPage) {
            tapOnSortBtn()
            tapOnNameAscBtn()
            val itemNameAsc = getFirstItemNameText()
            assertEquals(
                itemNameAsc,
                PRODUCT_NAME_ASCENDING,
                "The first item name is incorrect after sorting by name ascending"
            )
        }
    }

    @Test
    @Owner("Polishevskyi")
    fun `Verify product name when sorted by name descending`() {
        with(productsPage) {
            tapOnSortBtn()
            tapOnNameDescBtn()
            val itemNameDesc = getFirstItemNameText()
            assertEquals(
                itemNameDesc,
                PRODUCT_NAME_DESCENDING,
                "The first item name is incorrect after sorting by name descending"
            )
        }
    }

    @Test
    @Owner("Polishevskyi")
    fun `Verify product name when sorted by price ascending`() {
        with(productsPage) {
            tapOnSortBtn()
            tapOnPriceAscBtn()
            val itemPriceAsc = getFirstItemNameText()
            assertEquals(
                itemPriceAsc,
                PRODUCT_PRICE_ASCENDING,
                "The first item name is incorrect after sorting by price ascending"
            )
        }
    }

    @Test
    @Owner("Polishevskyi")
    fun `Verify product name when sorted by price descending`() {
        with(productsPage) {
            tapOnSortBtn()
            tapOnPriceDescBtn()
            val itemPriceDesc = getFirstItemNameText()
            assertEquals(
                itemPriceDesc,
                PRODUCT_PRICE_DESCENDING,
                "The first item name is incorrect after sorting by price descending"
            )
        }
    }
}
