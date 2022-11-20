plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    android()
    jvm("desktop")
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
                api(compose.uiTooling)
                api(compose.preview)

                api(libs.koin.core)
                api(libs.bundles.odyssey)
                api(libs.bundles.kviewmodel)
                api(libs.napier)
                api(libs.serialization)

                implementation(libs.sqldelight.coroutines)
                implementation(libs.bundles.ktor)
                implementation(libs.koalaplot)
            }
        }
        named("desktopMain") {
            dependencies {
                implementation(libs.sqldelight.jvm)
                implementation(libs.datepicker.desktop)
                implementation(libs.ktor.jvm)
            }
        }
        named("androidMain") {
            dependencies {
                implementation(libs.viewModel)
                implementation(libs.bundles.koin.android)
                implementation(libs.sqldelight.android)
                implementation(libs.ktor.android)
            }
        }
    }
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.finance_tracker.finance_tracker"
        schemaOutputDirectory = file("src/commonMain/sqldelight/com/finance_tracker/finance_tracker/schemas")
        verifyMigrations = true
    }
}