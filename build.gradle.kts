// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.koltin.serilization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.googleService) apply false
    alias(libs.plugins.crashlytics) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

// Read keystore file
fun credentialData(): java.util.Properties {
    val credentialProperties = java.util.Properties()
    credentialProperties.load(java.io.FileInputStream(project.rootProject.file("credential.properties")))
    return credentialProperties
}

// KEYSTORE CONFIG
ext {
    set("credentialStoreFile", credentialData()["storeFile"].toString())
    set("credentialStorePassword", credentialData()["storePassword"].toString())
    set("credentialKeyAlias", credentialData()["keyAlias"].toString())
    set("credentialKeyPassword", credentialData()["keyPassword"].toString())
}