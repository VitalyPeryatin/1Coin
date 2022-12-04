import io.gitlab.arturbosch.detekt.Detekt

plugins {
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
    detekt(libs.detekt.cli)
    detektPlugins(libs.detekt.rules.compose)
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