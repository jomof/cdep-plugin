package com.github.jomof.cdepplugin

import com.github.jomof.cdepplugin.dsl.CDepDsl
import com.github.jomof.cdepplugin.tasks.CMakeifyAndroidTask
import com.github.jomof.cdepplugin.tasks.CMakeifyLinuxTask
import com.github.jomof.cdepplugin.tasks.CMakeifyWindowsTask
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
    private var androidAbis = listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a")

    override fun apply(project: Project) {
        val extension = project.extensions.create("cdep", CDepDsl::class.java)
        val cmakeify = project.task("cmakeify")
        cmakeify.group = "cdep"

        for (abi in androidAbis) {
            cmakeify.dependsOn(
                    project.tasks.create("cmakeify-android-$abi", CMakeifyAndroidTask::class.java) {
                        it.abi = abi
                        it.buildOutputFolder = File(project.buildDir, "cdep/cmakeify/android/$abi")
                        it.stdout = File(it.buildOutputFolder, "stdout.txt")
                        it.stderr = File(it.buildOutputFolder, "stderr.txt")
                        it.exitcode = File(it.buildOutputFolder, "exitcode.txt")
                        it.group = "cdep"
                        it.tryStart()
                    })
        }

        cmakeify.dependsOn(
                project.tasks.create("cmakeify-linux", CMakeifyLinuxTask::class.java) {
                    it.buildOutputFolder = File(project.buildDir, "cdep/cmakeify/linux")
                    it.stdout = File(it.buildOutputFolder, "stdout.txt")
                    it.stderr = File(it.buildOutputFolder, "stderr.txt")
                    it.exitcode = File(it.buildOutputFolder, "exitcode.txt")
                    it.group = "cdep"
                    it.tryStart()
                })

        cmakeify.dependsOn(
                project.tasks.create("cmakeify-windows", CMakeifyWindowsTask::class.java) {
                    it.buildOutputFolder = File(project.buildDir, "cdep/cmakeify/windows")
                    it.stdout = File(it.buildOutputFolder, "stdout.txt")
                    it.stderr = File(it.buildOutputFolder, "stderr.txt")
                    it.exitcode = File(it.buildOutputFolder, "exitcode.txt")
                    it.group = "cdep"
                    it.tryStart()
                })

    }
}