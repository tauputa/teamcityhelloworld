import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

version = "2019.2"

project {
    params {
        // This makes it impossible to change the build settings through the UI
        param("teamcity.ui.settings.readOnly", "true")
    }

    buildType(Hello)
}

object Hello : BuildType({
    name = "Hello"

    vcs {
        // Use the source code from the same VCS root that the Kotlin DSL came from
        root(DslContext.settingsRoot.id!!)
        cleanCheckout = true
    }

    steps {
        script {
            name = "Hello World"
            scriptContent = """
                echo "hello world! I'm a build"
            """.trimIndent()
        }
    }

    triggers {
        vcs {
            branchFilter = "+:<default>"
        }
    }
})
