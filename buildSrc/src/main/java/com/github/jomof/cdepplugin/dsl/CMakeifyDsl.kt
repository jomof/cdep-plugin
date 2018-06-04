package com.github.jomof.cdepplugin.dsl

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.util.ConfigureUtil

class CMakeifyDsl {
    private val android: CMakeifyAndroidDsl = CMakeifyAndroidDsl()

    fun android(closure: Closure<CMakeifyAndroidDsl>) {
        ConfigureUtil.configure(closure, android)
    }

    fun android(action: Action<CMakeifyAndroidDsl>) {
        action.execute(android)
    }
}