plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.gradleplugins.android)
    implementation(libs.gradleplugins.kotlin)
    implementation(libs.gradleplugins.compose)
    implementation(libs.gradleplugins.serialization)
    implementation(libs.gradleplugins.detekt)
    implementation(libs.gradleplugins.google.services)
    implementation(libs.gradleplugins.firebase.crashlytics)
    implementation(libs.gradleplugins.sqldelight)
}