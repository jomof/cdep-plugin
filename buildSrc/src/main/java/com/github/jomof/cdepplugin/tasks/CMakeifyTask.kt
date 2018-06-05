package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import java.io.File

open class CMakeifyTask : DefaultTask() {
    var buildOutputFolder: File? = null
    fun commonFlags(): List<Pair<String, String>> {
        return listOf(
                "-H${buildOutputFolder!!.parentFile}" to "",
                "-B$buildOutputFolder" to ""
        )
    }

}