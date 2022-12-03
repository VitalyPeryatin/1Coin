plugins {
    id("android-setup")
    id("multiplatform-compose-setup")
    id("com.squareup.sqldelight")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlin.plugin.serialization")
}
android {
    signingConfigs {
        getByName("debug") {
            storeFile =
                file("/Users/vitalyperyatin/AndroidStudioProjects/FinanceTracker/1coin-debug.keystore")
            storePassword = "00000000"
            keyAlias = "1coin"
            keyPassword = "00000000"
        }
    }
    namespace = "com.finance_tracker.finance_tracker.common"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                api(libs.bundles.odyssey)
                api(libs.napier)
                api(libs.koin.core)

                implementation(libs.bundles.kviewmodel)
                implementation(libs.serialization)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.bundles.ktor)
                implementation(libs.bundles.paging)
                implementation(libs.koalaplot)
                implementation(libs.settings)
                implementation(libs.uuid)
            }
        }
        named("desktopMain") {
            dependencies {
                implementation(libs.sqldelight.jvm)
                implementation(libs.datepicker.desktop)
                implementation(libs.ktor.jvm)
                implementation(libs.slf4j)
                implementation(libs.json)
                implementation(libs.amplitude.java)
            }
        }
        named("androidMain") {
            dependencies {
                implementation(libs.viewModel)
                implementation(libs.bundles.koin.android)
                implementation(libs.sqldelight.android)
                implementation(libs.ktor.android)
                implementation(libs.amplitude.android)
            }
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