package com.github.jomof.cdepplugin.dsl

class ProvisioningCMakeDsl {
    private var executable: String = ""

    fun executable(value: String) {
        this.executable = value
    }
}