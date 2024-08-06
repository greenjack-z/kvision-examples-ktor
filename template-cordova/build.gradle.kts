import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("plugin.serialization") version kotlinVersion
    kotlin("multiplatform") version kotlinVersion
    val kvisionVersion: String by System.getProperties()
    id("io.kvision") version kvisionVersion
}

version = "1.0.0-SNAPSHOT"
group = "com.example"

repositories {
    mavenCentral()
    mavenLocal()
}

// Versions
val kotlinVersion: String by System.getProperties()
val kvisionVersion: String by System.getProperties()

val webDir = file("src/jsMain/web")

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    sourceSets["jsMain"].dependencies {
        implementation("io.kvision:kvision:$kvisionVersion")
        implementation("io.kvision:kvision-bootstrap:$kvisionVersion")
        implementation("io.kvision:kvision-i18n:$kvisionVersion")
        implementation("io.kvision:kvision-state-flow:$kvisionVersion")
        implementation("io.kvision:kvision-cordova:$kvisionVersion")
    }
    sourceSets["jsTest"].dependencies {
        implementation(kotlin("test-js"))
        implementation("io.kvision:kvision-testutils:$kvisionVersion")
    }
}

afterEvaluate {
    tasks {
        create("distCordova", Copy::class) {
            dependsOn("jsBrowserProductionWebpack")
            group = "package"
            doFirst {
                delete(fileTree(mapOf("dir" to "www", "exclude" to ".gitkeep")))
            }
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            val distribution =
                project.tasks.getByName("jsBrowserProductionWebpack", KotlinWebpack::class).outputDirectory
            from(distribution, webDir)
            inputs.files(distribution, webDir)
            into("www")
        }
    }
}
