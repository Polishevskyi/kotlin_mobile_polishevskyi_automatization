package base

import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import io.appium.java_client.service.local.flags.GeneralServerFlag
import java.io.File

object AppiumServer {

    private var server: AppiumDriverLocalService? = null

    private fun setInstance() {
        val builder = AppiumServiceBuilder().apply {
            withAppiumJS(File("/Users/serhii/.nvm/versions/node/v21.7.1/lib/node_modules/appium/build/lib/main.js"))
            usingDriverExecutable(File("/Users/serhii/.nvm/versions/node/v21.7.1/bin/node"))
            usingPort(4723)
            withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
            withLogFile(File("Appiumlog.txt"))
            withIPAddress("127.0.0.1")

            if (AppData.useGesturePlugin.contains("true")) {
                withArgument(GeneralServerFlag.USE_PLUGINS, "gestures")
            }

            if (AppData.chromeAutoDownloadDriver.contains("true")) {
                withArgument(GeneralServerFlag.ALLOW_INSECURE, "chromedriver_autodownload")
            }
        }

        server = AppiumDriverLocalService.buildService(builder)
    }

    private fun getInstance(): AppiumDriverLocalService {
        return server ?: run {
            setInstance()
            server!!
        }
    }

    fun start() {
        getInstance().start()
        println(getInstance().url)
        println(getInstance().isRunning)
    }

    fun stop() {
        server?.stop()
    }
}
