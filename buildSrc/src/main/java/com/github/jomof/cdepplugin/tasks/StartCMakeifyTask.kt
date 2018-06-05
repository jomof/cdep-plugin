package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import java.io.File

open class StartCMakeifyTask : DefaultTask() {
    var buildOutputFolder: File? = null
    var process: CompleteableProcess = CompleteableProcess()
}