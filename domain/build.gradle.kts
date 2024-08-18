plugins {
    id("java-library")
    alias(libs.plugins.koltin.serilization)
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.coroutine.core)
    implementation(libs.paging.common)
    implementation(libs.kotlinx.serialization)
}