package com.github.jomof.cdepplugin.tasks

import org.gradle.api.tasks.TaskAction

open class CMakeifyLinuxTask : CMakeifyTask() {
    @TaskAction
    fun consume() {
        if (process.process != null) {
            println("Waiting for process for Linux")
            val exit = process.process!!.waitFor()
            exitcode!!.writeText("$exit")
        }
    }

    fun tryStart() {
        if (cmake == null) {
            println("Not building for Linux because cmake was not found")
            return
        }
        if (cmakelists == null) {
            println("Not building for Linux because CMakeLists.txt was not found")
            return
        }
        println("CMake-ing Linux to $buildOutputFolder")
        buildOutputFolder!!.mkdirs()

        println("Starting process that outputs to $stdout")
        val builder = ProcessBuilder()
                .command(
                        cmake!!.absolutePath,
                        "-H${cmakelists!!.parent}",
                        "-B$buildOutputFolder",
                        "-DCMAKE_SYSTEM_NAME=Linux",
                        "-DCMAKE_BUILD_TYPE=Release",
                        "-DCMAKE_MAKE_PROGRAM=$ninja",
                        "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON",
                        "-GCodeBlocks - Ninja"
                )
                .redirectOutput(ProcessBuilder.Redirect.to(stdout))
                .redirectError(ProcessBuilder.Redirect.to(stderr))

        process.process = builder.start()
    }

}