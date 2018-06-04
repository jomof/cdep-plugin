import dsl.CDepDsl
import org.gradle.api.Plugin
import org.gradle.api.Project
import tasks.ConsumeCMakeTask
import tasks.ProvisionCMakeTask

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
    override fun apply(project: Project) {
        val extension = project.extensions.create(
                "cdep", CDepDsl::class.java)
        val provisionCMake =
                project.tasks.create("provision-cmake", ProvisionCMakeTask::class.java) {
                    val binary = project.files()
                    it.binary = binary
                    binary.setBuiltBy(listOf(it))
                    it.group = "cdep"
                }
        val consumeCMakeTask =
                project.tasks.create("consume-cmake", ConsumeCMakeTask::class.java) {
                    it.binary = provisionCMake.binary
                    it.group = "cdep"
                }
    }
}