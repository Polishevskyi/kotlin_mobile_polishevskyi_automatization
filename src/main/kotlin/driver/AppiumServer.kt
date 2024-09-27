package driver

import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import io.appium.java_client.service.local.flags.GeneralServerFlag
import java.io.File

object AppiumServer {

    private lateinit var server: AppiumDriverLocalService

    private fun setInstance() {
        val builder = AppiumServiceBuilder().apply {
            withAppiumJS(File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
            usingDriverExecutable(File("/usr/local/bin/node"))
            usingPort(4723)
            withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
            withLogFile(File("Appiumlog.txt"))
            withIPAddress("127.0.0.1")
        }

        server = AppiumDriverLocalService.buildService(builder)
    }

    private fun getInstance(): AppiumDriverLocalService {
        if (!::server.isInitialized) {
            setInstance()
        }
        return server
    }

    fun start() {
        getInstance().start()
    }

    fun stop() {
        if (::server.isInitialized && server.isRunning) {
            server.stop()
        }
    }
}
