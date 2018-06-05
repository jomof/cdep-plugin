package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class CompleteCMakeifyTask : DefaultTask() {
    var process: CompleteableProcess? = null

    @TaskAction
    fun complete() {
        process!!.process!!.waitFor()
        println("Completed ${process!!.process!!}")
    }
}