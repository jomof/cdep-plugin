package dsl

import org.gradle.api.Action

open class CDepDsl {
    private val provisioning: ProvisioningDsl = ProvisioningDsl()
    fun provisioning(action: Action<ProvisioningDsl>) {
        action.execute(provisioning)
    }
}