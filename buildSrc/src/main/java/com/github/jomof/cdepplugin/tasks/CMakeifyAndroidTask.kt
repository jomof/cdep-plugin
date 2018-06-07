package com.github.jomof.cdepplugin.tasks

import com.github.jomof.cdepplugin.provision.foundAndroidNdk
import org.gradle.api.tasks.TaskAction
import java.io.File

open class CMakeifyAndroidTask : CMakeifyTask() {
    private var androidNdk: File? = foundAndroidNdk
    var abi: String? = null

    @TaskAction
    fun consume() {
        if (process.process != null) {
            println("Waiting for process for Android ABI $abi")
            val exit = process.process!!.waitFor()
            exitcode!!.writeText("$exit")
        }
    }

    fun tryStart() {
        if (cmake == null) {
            println("Not building for Android because cmake was not found")
            return
        }
        if (androidNdk == null) {
            println("Not building for Android because NDK was not found")
            return
        }
        if (cmakelists == null) {
            println("Not building for Android because CMakeLists.txt was not found")
            return
        }
        println("CMake-ing Android $abi to $buildOutputFolder")
        buildOutputFolder!!.mkdirs()

        println("Starting process that outputs to $stdout")
        val builder = ProcessBuilder()
                .command(
                        cmake!!.absolutePath,
                        //"--version",
                        "-H${cmakelists!!.parent}",
                        "-B$buildOutputFolder",
                        "-DCMAKE_SYSTEM_NAME=Android",
                        "-DCMAKE_BUILD_TYPE=Release",
                        "-DANDROID_ABI=$abi",
                        "-DCMAKE_ANDROID_ARCH_ABI=$abi",
                        "-DANDROID_NDK=$androidNdk",
                        "-DCMAKE_MAKE_PROGRAM=$ninja",
                        "-DANDROID_PLATFORM=android-21",
                        "-DCMAKE_SYSTEM_VERSION=21",
                        "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON",
                        "-DCMAKE_TOOLCHAIN_FILE=$androidNdk/build/cmake/android.toolchain.cmake",
                        "-GCodeBlocks - Ninja"
                )
                .redirectOutput(ProcessBuilder.Redirect.to(stdout))
                .redirectError(ProcessBuilder.Redirect.to(stderr))

        process.process = builder.start()
    }
}