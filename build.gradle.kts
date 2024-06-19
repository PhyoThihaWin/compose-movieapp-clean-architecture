// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.koltinxSerilization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.googleService) apply false
    alias(libs.plugins.crashlytics) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

fun credentialData(): java.util.Properties {
    val credentialProperties = java.util.Properties()
    credentialProperties.load(java.io.FileInputStream(project.rootProject.file("credential.properties")))
    return credentialProperties
}

val credentialProperties = credentialData()

ext {
    // KEYSTORE CONFIG
    set("credentialStoreFile", credentialProperties["storeFile"].toString())
    set("credentialStorePassword", credentialProperties["storePassword"].toString())
    set("credentialKeyAlias", credentialProperties["keyAlias"].toString())
    set("credentialKeyPassword", credentialProperties["keyPassword"].toString())
}