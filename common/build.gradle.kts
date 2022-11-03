plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
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
            }
        }
        named("desktopMain") {
            dependencies {
                implementation(libs.sqldelight.jvm)
                implementation(libs.datepicker.desktop)
            }
        }
        named("androidMain") {
            dependencies {
                implementation(libs.viewModel)
                implementation(libs.bundles.koin.android)
                implementation(libs.sqldelight.android)
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
    }
}