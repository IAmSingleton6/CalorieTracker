apply(from = "$rootDir/gradle/githooks.gradle.kts")

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.detekt)
}

subprojects {
    afterEvaluate {
        tasks.findByName("preBuild")?.let { preBuild ->
            preBuild.dependsOn(rootProject.tasks.named("installGitHooks"))
        }
    }
}

detekt {
    parallel = true
    buildUponDefaultConfig = true
    config.setFrom(files("${project.rootDir}/quality/detekt.yml"))
    baseline = file("${project.rootDir}/quality/detekt-baseline.xml")
    source.setFrom(
        files("src/main/java", "src/test/java")
    )
}

tasks.register("allTests") {
    dependsOn(subprojects.flatMap { it.tasks.matching { it.name.endsWith("Test") } })
}
