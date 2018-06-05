package com.github.jomof.cdepplugin.tasks

import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

open class StartCMakeifyAndroidTask : StartCMakeifyTask() {

    @JvmField
    var cmake: ConfigurableFileCollection? = null

    @JvmField
    var ninja: ConfigurableFileCollection? = null

    @JvmField
    var ndk: ConfigurableFileCollection? = null

    @JvmField
    var cmakelists: ConfigurableFileCollection? = null

    @JvmField
    var abi: String? = null

    @JvmField
    var stdout: ConfigurableFileCollection? = null

    @JvmField
    var stderr: ConfigurableFileCollection? = null

    @InputFiles
    fun getCmake(): ConfigurableFileCollection = cmake!!

    @InputFiles
    fun getNinja(): ConfigurableFileCollection = ninja!!

    @InputFiles
    fun getNdk(): ConfigurableFileCollection = ndk!!

    @Input
    fun getAbi(): String = abi!!

    @InputFiles
    fun getCmakelists() = cmakelists!!

    @OutputFiles
    fun getStdout() = stdout!!

    @OutputFiles
    fun getStderr() = stderr!!

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
        println("CMake-ing Android $abi to $buildOutputFolder")
        buildOutputFolder!!.mkdirs()

        println("Starting process that outputs to ${stdout!!.singleFile}")
        val builder = ProcessBuilder()
                .command(
                        cmake!!.singleFile.absolutePath,
                        //"--version",
                        "-H${cmakelists!!.singleFile.parent}",
                        "-B$buildOutputFolder",
                        "-DCMAKE_SYSTEM_NAME=Android",
                        "-DCMAKE_BUILD_TYPE=Release",
                        "-DANDROID_ABI=$abi",
                        "-DCMAKE_ANDROID_ARCH_ABI=$abi",
                        "-DANDROID_NDK=${ndk!!.singleFile.absolutePath}",
                        "-DCMAKE_MAKE_PROGRAM=${getNinja().singleFile}",
                        "-DANDROID_PLATFORM=android-21",
                        "-DCMAKE_SYSTEM_VERSION=21",
                        "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON",
                        "-DCMAKE_TOOLCHAIN_FILE=${ndk!!.singleFile.absolutePath}/build/cmake/android.toolchain.cmake",
                        "-GNinja"
                )
                .redirectOutput(ProcessBuilder.Redirect.to(stdout!!.singleFile))
                .redirectError(ProcessBuilder.Redirect.to(stderr!!.singleFile))

        process.process = builder.start()
    }
}