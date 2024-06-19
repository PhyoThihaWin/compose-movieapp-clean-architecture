plugins {
    id("java-library")
    alias(libs.plugins.koltinxSerilization)
    alias(libs.plugins.jetbrainsKotlinJvm)
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