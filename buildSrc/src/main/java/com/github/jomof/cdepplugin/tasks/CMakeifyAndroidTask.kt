package com.github.jomof.cdepplugin.tasks

import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

open class CMakeifyAndroidTask : CMakeifyTask() {

    @JvmField
    var cmake: ConfigurableFileCollection? = null

    @JvmField
    var ndk: ConfigurableFileCollection? = null

    @InputFiles
    fun getBinary(): ConfigurableFileCollection = cmake!!

    @InputFiles
    fun getNdk(): ConfigurableFileCollection = ndk!!

    @JvmField
    var abi: String? = null

    @Input
    fun getAbi(): String = abi!!

    @JvmField
    var cmakelists: ConfigurableFileCollection? = null

    @InputFiles
    fun getCmakelists() = cmakelists!!

    @TaskAction
    fun consume() {
        if (cmake!!.isEmpty) {
            println("Not building for Android because cmake was not found")
            return
        }
        if (ndk!!.isEmpty) {
            println("Not building for Android because NDK was not found")
            return
        }
        if (cmakelists!!.isEmpty) {
            println("Not building for Android because CMakeLists.txt was not found")
            return
        }
        println("CMake-ing Android $abi")
        val flags = listOf(
                "-H${cmakelists!!.singleFile}" to "",
                "-B$buildOutputFolder" to "",
                "-DANDROID_ABI" to abi!!,
                "-DANDROID_PLATFORM" to "android-21",
                "-DCMAKE_BUILD_TYPE" to "Release",
                "-DANDROID_NDK" to ndk!!.singleFile.absolutePath,
                "-DCMAKE_SYSTEM_NAME" to "Android",
                "-DCMAKE_ANDROID_ARCH_ABI" to abi!!,
                "-DCMAKE_SYSTEM_VERSION" to "21",
                "-DCMAKE_EXPORT_COMPILE_COMMANDS" to "ON",
                "-DCMAKE_ANDROID_NDK" to ndk!!.singleFile.absolutePath,
                "-DCMAKE_TOOLCHAIN_FILE" to "${ndk!!.singleFile.absolutePath}/build/cmake/android.toolchain.cmake",
                "-G Ninja"
        )
        println("flags=$flags")
    }
}