package build

import versions.Versions
import versions.Versions.hilt_version
import versions.Versions.nav_version

object Build {
    const val build_tools = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt_gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:$hilt_version"
    const val safe_args_gradle_plugin =  "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"
}