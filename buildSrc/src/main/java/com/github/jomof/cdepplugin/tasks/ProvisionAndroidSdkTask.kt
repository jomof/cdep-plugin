package com.github.jomof.cdepplugin.tasks

import com.github.jomof.cdepplugin.provision.userFolder
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ProvisionAndroidSdkTask : DefaultTask() {
    @JvmField
    var sdk: ConfigurableFileCollection? = null

    @OutputFiles
    fun sdk(): ConfigurableFileCollection = sdk!!

    @TaskAction
    fun provision() {
        val userFolderSdk = File(userFolder(), "Android/Sdk")
        if (userFolderSdk.isDirectory) {
            sdk!!.setFrom(userFolderSdk)
            println("Found Android SDK at $userFolderSdk")
            return
        }
    }
}