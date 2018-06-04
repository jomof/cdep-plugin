package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

open class ProvisionEverythingTask : DefaultTask() {
    @JvmField
    var cmake: ConfigurableFileCollection? = null
    @JvmField
    var ndk: ConfigurableFileCollection? = null

    @InputFiles
    fun getBinary(): ConfigurableFileCollection = cmake!!

    @InputFiles
    fun getNdk(): ConfigurableFileCollection = ndk!!

    @TaskAction
    fun consume() {
        //throw RuntimeException(cmake!!.singleFile.toString())
    }
}