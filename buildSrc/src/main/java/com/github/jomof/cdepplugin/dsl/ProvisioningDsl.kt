package com.github.jomof.cdepplugin.dsl

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.util.ConfigureUtil.configure

class ProvisioningDsl {
    private val cmake: ProvisioningCMakeDsl = ProvisioningCMakeDsl()

    fun cmake(closure: Closure<ProvisioningCMakeDsl>) {
        configure(closure, cmake)
    }

    fun cmake(action: Action<ProvisioningCMakeDsl>) {
        action.execute(cmake)
    }
}