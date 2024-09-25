# Kotlin Mobile Automation Project

This project automates mobile application testing using Kotlin, Appium, Selenium, and TestNG. It supports testing on both Android and iOS platforms, with integration for BrowserStack for cloud-based testing.

## Project Structure

- **driver**: Contains classes for setting up and managing drivers for Android and iOS platforms using Appium.
- **pages**: Implements the Page Object Model (POM) pattern for different pages in the app.
- **utils**: Includes utility classes such as data generators and data providers for TestNG.
- **tests**: Contains the test cases, which verify various mobile application functionalities.

## Key Components

### 1. **AppData**
- Manages platform-specific settings (Android/iOS) and other properties like the usage of cloud services.

### 2. **AppDriver**
- Handles driver management using `ThreadLocal` to support parallel execution.

### 3. **AppFactory**
- Provides functionality for launching and closing apps on both Android and iOS devices. Supports local testing and cloud-based testing using BrowserStack.

### 4. **AppiumServer**
- Manages the lifecycle of the Appium server, including options for using the gesture plugin and auto-downloading Chromedriver.

### 5. **Pages (Page Object Model)**
- Implements a POM structure for handling interactions with elements on the application’s different screens.
- Example: `ProductsPage`, `LoginPage`, `CartPage`, etc.

### 6. **Utilities**
- `DataGenerator`: Generates random data using `Faker` for testing purposes (emails, passwords, etc.).
- `DataProviders`: Provides data for parameterized TestNG tests.

## Prerequisites

Before running the tests, make sure the following dependencies are installed on your system:

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Node.js](https://nodejs.org/en/) (for Appium)
- [Appium](https://appium.io/)
- [Maven](https://maven.apache.org/)
- [Allure](https://docs.qameta.io/allure/) (for test reports)

## Setup

### 1. Install Appium

Install Appium globally using NPM:

```bash
npm install -g appium
```
### 2. Install Appium Drivers

Ensure you have the necessary drivers installed for Android and iOS:

```bash
appium driver install uiautomator2
appium driver install xcuitest
```
### 3. Set up BrowserStack Integration

To run tests on BrowserStack, ensure you have the required credentials (`browserstack.userName`, `browserstack.accessKey`) in the `keystore.properties` file:

```properties
browserstack.userName=your_browserstack_username
browserstack.accessKey=your_browserstack_access_key
browserstack.androidApp=bs://<app-id>
browserstack.iosApp=bs://<app-id>
```
After uploading the app, you can find the `app-id` for your app in the BrowserStack dashboard.

## Running Tests

### 1. Start Appium Server

Start the Appium server locally:

```bash
appium
```
### 2. Execute Tests

Run the tests using Maven:

```bash
mvn clean test
```
This command will clean any previous build artifacts and execute all the tests defined in the project.

### 3. Generate Allure Report

After running tests, you can generate and serve an Allure report:

```bash
mvn allure:report
mvn allure:serve
```
The first command will generate the Allure report from the test results, and the second command will launch a local server to view the report in your browser.

## Test Scenarios

- **Login Tests**: Verifies login functionality, including valid and invalid credentials.
- **Product Cart Tests**: Verifies adding products to the cart and checking prices.
- **Sort Tests**: Tests sorting products by name and price in ascending/descending order.

## Parallel Test Execution

The tests can be run in parallel. The `testng.xml` configuration is set to run tests in parallel by classes:

```xml
<suite name="suite_ui_tests">
    <test name="UI tests" parallel="classes" thread-count="3">
        <classes>
            <class name="tests.LoginTests"/>
            <class name="tests.ProductCartTest"/>
            <class name="tests.SortTests"/>
        </classes>
    </test>
</suite>
```
## Tools and Libraries

- **Kotlin**: Main programming language used for writing the tests.
- **Appium**: Used for mobile automation on both Android and iOS.
- **Selenium**: Used for WebDriver management.
- **TestNG**: Test framework for structuring and executing tests.
- **Allure**: Used for generating reports.
- **Java Faker**: For generating random test data.

## CI/CD Integration

You can integrate this project into a CI/CD pipeline using tools like Jenkins, GitHub Actions, or any other CI server that supports Maven. Ensure the following tasks are part of the pipeline:
- Install dependencies.
- Start the Appium server.
- Execute tests.
- Generate and archive Allure reports.

## Contribution

Please contribute to the project by submitting any issues or pull requests. Before contributing, please ensure your code follows the existing structure and passes all tests.

## License

This project is licensed under the MIT License.

## Test Results Allure
![Test Results](https://github.com/Polishevskyi/kotlin_mobile_polishevskyi_automatization/blob/master/AllureReport.png)

## Test Results BrowserStack
![Test Results](https://github.com/Polishevskyi/kotlin_mobile_polishevskyi_automatization/blob/master/browserstack1.png)
![Test Results](https://github.com/Polishevskyi/kotlin_mobile_polishevskyi_automatization/blob/master/browserstack2.png)


