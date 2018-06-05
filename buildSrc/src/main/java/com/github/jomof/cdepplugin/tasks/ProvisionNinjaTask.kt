package com.github.jomof.cdepplugin.tasks

import com.github.jomof.cdepplugin.provision.findExecutableOnPath
import com.github.jomof.cdepplugin.provision.isWindows
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ProvisionNinjaTask : DefaultTask() {
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
        val exe = if (isWindows()) "ninja.exe" else "ninja"
        val ninja = findExecutableOnPath(exe)
        if (ninja != null) {
            println("Found ninja on PATH at $ninja")
            binary!!.setFrom(ninja)
            return
        }
        if (!sdk!!.isEmpty) {
            val sdkPath: File = sdk!!.singleFile
            val sdkCMakesDir = File(sdkPath, "cmake")
            if (sdkCMakesDir.isDirectory) {

                val ninjaFound = sdkCMakesDir.walk().mapNotNull { file ->
                    if (file.isFile && file.name == exe && file.parentFile.name == "bin") {
                        file
                    } else {
                        null
                    }
                }
                        .sortedByDescending { it.parentFile.name }
                        .singleOrNull()
                if (ninjaFound != null) {
                    println("Found ninja in Android NDK at $ninjaFound")
                    binary!!.setFrom(ninjaFound)
                    return
                }
            }
        }
    }
}