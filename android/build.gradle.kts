import java.io.FileInputStream
import java.util.Properties

plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    id("com.google.firebase.crashlytics")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.gms.google-services")
    kotlin("android")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("../1coin-debug.keystore")
            storePassword = "00000000"
            keyPassword = "00000000"
            keyAlias = "1coin"
        }
        create("release") {
            storeFile = file("../1coin.keystore")
            storePassword = keystoreProperties["storePassword"] as? String
            keyPassword = keystoreProperties["keyPassword"] as? String
            keyAlias = keystoreProperties["keyAlias"] as? String
        }
    }
    namespace = "com.finance_tracker.finance_tracker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.finance_tracker.finance_tracker"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}

dependencies {
    implementation(projects.common)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)

    debugImplementation(libs.leakcanary)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)

    implementation(libs.bundles.koin.android)
}