package utils

import org.testng.annotations.DataProvider
import utils.StringHelper.ERROR_INVALID_CREDENTIALS
import utils.StringHelper.VALID_PASSWORD
import utils.StringHelper.VALID_USERNAME

class DataProviders {

    private val dataGenerator = DataGenerator()

    @DataProvider(name = "invalid-login-dataProvider")
    fun invalidLoginDataProvider(): Array<Array<Any>> {
        return arrayOf(
            arrayOf(dataGenerator.generateRandomEmail(), VALID_PASSWORD, ERROR_INVALID_CREDENTIALS),
            arrayOf(VALID_USERNAME, dataGenerator.generateRandomPassword(), ERROR_INVALID_CREDENTIALS),
            arrayOf(
                dataGenerator.generateRandomEmail(),
                dataGenerator.generateRandomPassword(),
                ERROR_INVALID_CREDENTIALS
            )
        )
    }
}
