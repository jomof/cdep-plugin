package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ProvisionCMakeTask : DefaultTask() {
    @JvmField
    var binary: ConfigurableFileCollection? = null

    @OutputFiles
    fun binary(): ConfigurableFileCollection = binary!!

    @TaskAction
    fun provision() {
        throw RuntimeException("bob")
        binary!!.from(File("."))
    }
}