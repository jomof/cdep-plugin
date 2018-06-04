package com.github.jomof.cdepplugin.tasks

import com.github.jomof.cdepplugin.provision.findExecutableOnPath
import com.github.jomof.cdepplugin.provision.isWindows
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ProvisionCMakeTask : DefaultTask() {
    @JvmField
    var binary: ConfigurableFileCollection? = null

    @OutputFiles
    fun binary(): ConfigurableFileCollection = binary!!

    @JvmField
    var sdk: ConfigurableFileCollection? = null

    @InputFiles
    fun getSdk(): ConfigurableFileCollection = sdk!!


    @TaskAction
    fun provision() {
        val exe = if (isWindows()) "cmake.exe" else "cmake"
        val cmake = findExecutableOnPath(exe)
        if (cmake != null) {
            println("Found CMake on PATH at $cmake")
            binary!!.setFrom(cmake)
            return
        }
        if (!sdk!!.isEmpty) {
            val sdkPath: File = sdk!!.singleFile
            val sdkCMakesDir = File(sdkPath, "cmake")
            if (sdkCMakesDir.isDirectory) {

                val cmakeFound = sdkCMakesDir.walk().mapNotNull { file ->
                    if (file.isFile && file.name == exe && file.parentFile.name == "bin") {
                        println("Looking at $file comparing to $exe")
                        file
                    } else {
                        null
                    }
                }
                        .sortedByDescending { it.parentFile.name }
                        .singleOrNull()
                if (cmakeFound != null) {
                    println("Found CMake in Android NDK at $cmakeFound")
                    binary!!.setFrom(cmakeFound)
                    return
                }
            }
        }
    }
}