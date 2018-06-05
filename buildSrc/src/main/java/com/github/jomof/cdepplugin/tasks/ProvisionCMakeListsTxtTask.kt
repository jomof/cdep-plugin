package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ProvisionCMakeListsTxtTask : DefaultTask() {
    @JvmField
    var cmakelists: ConfigurableFileCollection? = null

    @OutputFiles
    fun cmakelists(): ConfigurableFileCollection = cmakelists!!

    @TaskAction
    fun provision() {
        val cmakelists = File("./CMakeLists.txt").absoluteFile
        if (cmakelists.exists()) {
            println("Found CMakeLists.txt at $cmakelists")
            this.cmakelists!!.setFrom(cmakelists)
        }
    }
}