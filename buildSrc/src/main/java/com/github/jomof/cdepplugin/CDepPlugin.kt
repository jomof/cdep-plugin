package com.github.jomof.cdepplugin

import com.github.jomof.cdepplugin.dsl.CDepDsl
import com.github.jomof.cdepplugin.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class CDepPlugin : Plugin<Project> {
    /*
    cdep {
        provisioning {
          cmake {
          }
          ndk {
            path "path/to/ndk"
            version "r15"
            download true
          }
        }
        cmakeify {
          path "CMakeLists.txt"
          host {
            linux {
              build "android"
            }
            mac {
              build "iOS"
            }
            windows {
              build "android"
            }
          }
          android {
            x86 {
            }
          }
        }
        deploy {
          github { }
          local {
            folder {
            }
          }
        }
    }
    */
    var androidAbis = listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a")

    override fun apply(project: Project) {
        val extension = project.extensions.create("cdep", CDepDsl::class.java)
        val provisionCMakeLists =
                project.tasks.create("provision-cmakelists", ProvisionCMakeListsTxtTask::class.java) {
                    val cmakelists = project.files()
                    it.cmakelists = cmakelists
                    cmakelists.setBuiltBy(listOf(it))
                    it.group = "cdep"
                }
        val provisionSdk =
                project.tasks.create("provision-android-sdk", ProvisionAndroidSdkTask::class.java) {
                    val sdk = project.files()
                    it.sdk = sdk
                    sdk.setBuiltBy(listOf(it))
                    it.group = "cdep"
                }
        val provisionCMake =
                project.tasks.create("provision-cmake", ProvisionCMakeTask::class.java) {
                    val binary = project.files()
                    it.binary = binary
                    it.sdk = provisionSdk.sdk
                    binary.setBuiltBy(listOf(it))
                    it.group = "cdep"
                }
        val provisionNdk =
                project.tasks.create("provision-android-ndk", ProvisionAndroidNdkTask::class.java) {
                    val ndk = project.files()
                    it.ndk = ndk
                    it.sdk = provisionSdk.sdk
                    ndk.setBuiltBy(listOf(it))
                    it.group = "cdep"
                }
        val consumeEverythingTask =
                project.tasks.create("provision-everything", ProvisionEverythingTask::class.java) {
                    it.cmake = provisionCMake.binary
                    it.ndk = provisionNdk.ndk
                    it.group = "cdep"
                }

        var cmakeifyAndroid = project.task("cmakeify-android")
        cmakeifyAndroid.group = "cdep"

        for (abi in androidAbis) {
            var task =
                    project.tasks.create("cmakeify-android-$abi", CMakeifyAndroidTask::class.java) {
                        it.cmake = provisionCMake.binary
                        it.ndk = provisionNdk.ndk
                        it.abi = abi
                        it.cmakelists = provisionCMakeLists.cmakelists
                        it.buildOutputFolder = File(project.buildDir, "android/$abi")
                        it.group = "cdep"
                    }
            cmakeifyAndroid.dependsOn(task)
        }

    }
}