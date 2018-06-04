package com.github.jomof.cdepplugin.dsl

import groovy.lang.Closure
import org.gradle.api.Action
import org.gradle.util.ConfigureUtil.configure

open class CDepDsl {
    private val provisioning: ProvisioningDsl = ProvisioningDsl()

    fun provisioning(closure: Closure<ProvisioningDsl>) {
        configure(closure, provisioning)
    }

    fun provisioning(action: Action<ProvisioningDsl>) {
        action.execute(provisioning)
    }

    private val cmakeify: CMakeifyDsl = CMakeifyDsl()
    fun cmakeify(closure: Closure<CMakeifyDsl>) {
        configure(closure, cmakeify)
    }

    fun cmakeify(action: Action<CMakeifyDsl>) {
        action.execute(cmakeify)
    }
}