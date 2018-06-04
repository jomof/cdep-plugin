package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction

open class ConsumeCMakeTask : DefaultTask() {
    @JvmField
    var binary: ConfigurableFileCollection? = null

    @InputFiles
    fun getBinary(): ConfigurableFileCollection = binary!!

    @TaskAction
    fun consume() {

    }
}