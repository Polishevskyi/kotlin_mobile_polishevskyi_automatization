package utils

import org.testng.annotations.DataProvider

class DataProviders {

    @DataProvider(name = "invalid-login-dataProvider")
    fun invalidLoginDataProvider(): Array<Array<Any>> {
        return arrayOf(
            arrayOf(StringHelper.VALID_USERNAME, "1234", StringHelper.ERROR_INVALID_CREDENTIALS),
            arrayOf("bob@example", "1234", StringHelper.ERROR_INVALID_CREDENTIALS)
        )
    }
}
