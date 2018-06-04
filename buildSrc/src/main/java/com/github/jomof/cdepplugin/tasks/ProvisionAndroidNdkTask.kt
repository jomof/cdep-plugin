package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File


open class ProvisionAndroidNdkTask : DefaultTask() {
    @JvmField
    var ndk: ConfigurableFileCollection? = null

    @OutputFiles
    fun ndk(): ConfigurableFileCollection = ndk!!

    @JvmField
    var sdk: ConfigurableFileCollection? = null

    @InputFiles
    fun getSdk(): ConfigurableFileCollection = sdk!!

    @TaskAction
    fun provision() {
        if (!sdk!!.isEmpty) {
            val sdkNdk = File(sdk!!.singleFile, "Android/Sdk/ndk-bundle")
            ndk!!.setFrom(sdkNdk)
            println("Found Android NDK at $sdkNdk")
        }
    }
}