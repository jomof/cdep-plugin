package com.github.jomof.cdepplugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.Input
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


    @JvmField
    var abi: String? = null

    @Input
    fun getAbi(): String = abi!!

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
        println("CMake-ing Android $abi")

        /*
        arguments :
            -H/usr/local/google/home/jomof/AndroidStudioProjects/MyApplication3/app
            -B/usr/local/google/home/jomof/AndroidStudioProjects/MyApplication3/app/.externalNativeBuild/cmake/release/x86_64
            -DANDROID_ABI=x86_64
            -DANDROID_PLATFORM=android-21
            -DCMAKE_LIBRARY_OUTPUT_DIRECTORY=/usr/local/google/home/jomof/AndroidStudioProjects/MyApplication3/app/build/intermediates/cmake/release/obj/x86_64
            -DCMAKE_BUILD_TYPE=Release
            -DANDROID_NDK=/usr/local/google/home/jomof/Android/Sdk/ndk-bundle
            -DCMAKE_CXX_FLAGS=-std=c++14 -Ofast
            -DCMAKE_SYSTEM_NAME=Android
            -DCMAKE_ANDROID_ARCH_ABI=x86_64
            -DCMAKE_SYSTEM_VERSION=21
            -DCMAKE_EXPORT_COMPILE_COMMANDS=ON
            -DCMAKE_ANDROID_NDK=/usr/local/google/home/jomof/Android/Sdk/ndk-bundle
            -DCMAKE_TOOLCHAIN_FILE=/usr/local/google/home/jomof/Android/Sdk/ndk-bundle/build/cmake/android.toolchain.cmake
            -G Ninja
            -DCMAKE_MAKE_PROGRAM=/usr/local/google/home/jomof/Android/Sdk/cmake/3.6.4111459/bin/ninja
            jvmArgs :
         */
    }
}