package com.github.jomof.cdepplugin.tasks

import com.github.jomof.cdepplugin.provision.foundCMake
import com.github.jomof.cdepplugin.provision.foundCmakelists
import com.github.jomof.cdepplugin.provision.foundNinja
import org.gradle.api.DefaultTask
import java.io.File

open class CMakeifyTask : DefaultTask() {
    var buildOutputFolder: File? = null
    var cmake: File? = foundCMake
    var ninja: File? = foundNinja
    var cmakelists: File? = foundCmakelists
    var stdout: File? = null
    var stderr: File? = null
    var exitcode: File? = null

    var process: CompleteableProcess = CompleteableProcess()
}