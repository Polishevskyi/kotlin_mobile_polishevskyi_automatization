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
}