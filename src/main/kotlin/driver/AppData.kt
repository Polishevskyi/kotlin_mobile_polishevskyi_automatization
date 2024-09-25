package driver

object AppData {
    val platform: String = System.getProperty("platform", "ios")
    val isCloud: String = System.getProperty("isCloud", "false")
}

/*
Platform:
android
ios

Property:
true
false
*/
