package com.github.jomof.cdepplugin.provision

import java.io.File

fun findExecutableOnPath(exe: String): File? {
    val envPath = System.getenv("PATH")
    val splitPath = envPath.split(File.pathSeparator)
    for (path in splitPath) {
        val fullPath = File(path, exe)
        if (fullPath.exists()) {
            return fullPath
        }
    }
    return null
}