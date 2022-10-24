import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(projects.common)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.finance_tracker.finance_tracker.MainKt"

        nativeDistributions {
            modules("java.sql")
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "1Coin"
            packageVersion = "1.0.0"
            copyright = "© 2021 1Coin. All rights reserved."
            vendor = "FinanceTracker"
            licenseFile.set(rootProject.file("LICENSE"))
            macOS {
                iconFile.set(project.file("src/jvmMain/resources/icons/app_icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/jvmMain/resources/icons/app_icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/jvmMain/resources/icons/app_icon.png"))
            }

            windows {
                menuGroup = "Desktop"
                upgradeUuid = "7abd45e8-22c0-11ec-9621-0242ac270000"
            }
        }
    }
}