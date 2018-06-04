package com.github.jomof.cdepplugin.provision

import java.io.File

fun isWindows() = System.getProperty("os.name").startsWith("Windows")
fun userFolder() = File(System.getProperty("user.home"))