package com.github.jomof.cdepplugin.provision

import java.io.File

val userFolder: File by lazy {
    val result = File(System.getProperty("user.home"))
    println("User folder is $result")
    result
}

val foundAndroidSdk: File? by lazy {
    if (!isWindows()) {
        val userFolderSdk = File(userFolder, "Android/Sdk")
        if (userFolderSdk.isDirectory) {
            println("Found Android SDK at $userFolderSdk")
            userFolderSdk
        } else {
            null
        }
    } else {
        val userFolderSdk = File(userFolder, "AppData/Local/Android/Sdk")
        if (userFolderSdk.isDirectory) {
            println("Found Android SDK at $userFolderSdk")
            userFolderSdk
        } else {
            null
        }
    }
}

val foundAndroidNdk: File? by lazy {
    if (foundAndroidSdk != null) {
        val sdkNdk = File(foundAndroidSdk, "ndk-bundle")
        println("Found Android NDK at $sdkNdk")
        sdkNdk
    } else {
        null
    }
}

val foundCMake: File? by lazy { findExe("cmake") }

val foundNinja: File? by lazy { findExe("ninja") }

private fun findExe(basename: String): File? {
    val exe = if (isWindows()) "$basename.exe" else basename
    val cmake = findExecutableOnPath(exe)
    if (cmake != null) {
        println("Found $basename on PATH at $cmake")
        return cmake
    }
    if (foundAndroidSdk != null) {
        val sdkCMakesDir = File(foundAndroidSdk, "cmake")
        if (sdkCMakesDir.isDirectory) {
            val exeFound = sdkCMakesDir.walk().mapNotNull { file ->
                if (file.isFile && file.name == exe && file.parentFile.name == "bin") {
                    file
                } else {
                    null
                }
            }
                    .sortedByDescending { it.parentFile.name }
                    .singleOrNull()
            if (exeFound != null) {
                println("Found $basename in Android NDK at $exeFound")
                return exeFound
            }
        }
    }
    return null
}

val foundCmakelists: File? by lazy {
    val cmakelists = File("./CMakeLists.txt").absoluteFile
    if (cmakelists.exists()) {
        println("Found CMakeLists.txt at $cmakelists")
        cmakelists
    } else {
        null
    }
}