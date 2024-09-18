package driver

object AppData {
    val platform: String = System.getProperty("platform", "ios")
    val useGesturePlugin: String = System.getProperty("useGesturePlugin", "true")
    val chromeAutoDownloadDriver: String = System.getProperty("chromeAutoDownloadDriver", "true")
    val isCloud: String = System.getProperty("isCloud", "true")
}

/*
Platform:
android
ios

Property:
true
false
*/
