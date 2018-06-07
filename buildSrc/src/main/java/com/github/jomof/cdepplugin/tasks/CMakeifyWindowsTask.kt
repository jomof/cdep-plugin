package com.github.jomof.cdepplugin.tasks

import org.gradle.api.tasks.TaskAction

open class CMakeifyWindowsTask : CMakeifyTask() {
    @TaskAction
    fun consume() {
        if (process.process != null) {
            println("Waiting for process for Windows")
            val exit = process.process!!.waitFor()
            exitcode!!.writeText("$exit")
        }
    }

    fun tryStart() {
        if (cmake == null) {
            println("Not building for Windows because cmake was not found")
            return
        }
        if (cmakelists == null) {
            println("Not building for Windows because CMakeLists.txt was not found")
            return
        }
        println("CMake-ing Windows to $buildOutputFolder")
        buildOutputFolder!!.mkdirs()

        println("Starting process that outputs to $stdout")
        val builder = ProcessBuilder()
                .command(
                        cmake!!.absolutePath,
                        "-H${cmakelists!!.parent}",
                        "-B$buildOutputFolder",
                        "-DCMAKE_SYSTEM_NAME=Windows",
                        "-DCMAKE_BUILD_TYPE=Release",
                        "-DCMAKE_MAKE_PROGRAM=$ninja",
                        "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON",
                        "-GVisual Studio 14 2015-"
                )
                .redirectOutput(ProcessBuilder.Redirect.to(stdout))
                .redirectError(ProcessBuilder.Redirect.to(stderr))

        process.process = builder.start()
    }

}