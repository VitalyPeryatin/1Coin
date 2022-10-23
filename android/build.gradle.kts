import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension

plugins {
    id("org.jetbrains.compose") version "1.2.0"
    id("com.android.application")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("io.gitlab.arturbosch.detekt")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.finance_tracker.finance_tracker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.finance_tracker.finance_tracker"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            (this as ExtensionAware).configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = false
            }
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
}

dependencies {

    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.core:core-ktx:1.9.0")

    // Firebase BoM
    implementation(platform ("com.google.firebase:firebase-bom:30.3.2"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")

    // Chucker
    val chukerVersion = "3.5.2"
    debugImplementation("com.github.chuckerteam.chucker:library:$chukerVersion")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chukerVersion")

    implementation(libs.bundles.koin.android)
}