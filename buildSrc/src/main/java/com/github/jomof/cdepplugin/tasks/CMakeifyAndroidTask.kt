package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

open class CMakeifyAndroidTask : DefaultTask() {

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
        if (cmake!!.isEmpty) {
            println("Not building for Android because cmake was not found")
            return
        }
        if (ndk!!.isEmpty) {
            println("Not building for Android because NDK was not found")
            return
        }
    }
}