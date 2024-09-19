package utils

import com.github.javafaker.Faker

class DataGenerator {
    private val faker = Faker()

    fun generateRandomEmail(): String {
        return faker.internet().emailAddress()
    }

    fun generateRandomPassword(): String {
        return faker.internet().password(8, 16)
    }

    fun generateLoginWithoutDomain(): String {
        return faker.name().username()
    }

    fun generateLoginWithoutAtSymbol(): String {
        return faker.name().username().replace("@", "")
    }

    fun generateSqlInjection(): String {
        return "' OR '1'='1"
    }

    fun generateXssAttack(): String {
        return "<script>alert('XSS');</script>"
    }
}