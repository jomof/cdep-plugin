import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class MaTask : DefaultTask()
class CDepPlugin : Plugin<Project> {
    /*
    cdep {
        provisioning {
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
    override fun apply(target: Project?) {
        //throw RuntimeException("nate-o potato mushroomette")
    }
}