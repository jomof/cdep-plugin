package dsl

import org.gradle.api.Action

class ProvisioningDsl {
    private val cmake: CMakeDsl = CMakeDsl()
    fun cmake(action: Action<CMakeDsl>) {
        action.execute(cmake)
    }
}