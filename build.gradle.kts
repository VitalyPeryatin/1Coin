import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.2")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.4")
    }
}

plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
    id("org.jetbrains.kotlin.plugin.serialization") apply false
    id("io.gitlab.arturbosch.detekt")
}

tasks.register<Detekt>("detektAll") {
    parallel = true
    setSource(projectDir)
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
    config.setFrom(project.file("config/detekt/detekt.yml"))
}

dependencies {
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:1.22.0-RC3")
    detektPlugins("ru.kode:detekt-rules-compose:1.2.2")
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    afterEvaluate {
        // Remove log pollution until Android support in KMP improves.
        project.extensions.findByType<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>()?.let { kmpExt ->
            kmpExt.sourceSets.removeAll { sourceSet ->
                setOf(
                    "androidAndroidTestRelease",
                    "androidTestFixtures",
                    "androidTestFixturesDebug",
                    "androidTestFixturesRelease",
                ).contains(sourceSet.name)
            }
        }
    }
}

task<Copy>("enableGitHooks") {
    group = "git hooks"
    from("${rootProject.rootDir}/hooks/")
    include("*")
    into("${rootProject.rootDir}/.git/hooks")
    fileMode = 0b111101101 // make files executable
}

task<Delete>("disableGitHooks") {
    group = "git hooks"
    delete = setOf(
        fileTree("${rootProject.rootDir}/.git/hooks/") {
            include("*")
        }
    )
}