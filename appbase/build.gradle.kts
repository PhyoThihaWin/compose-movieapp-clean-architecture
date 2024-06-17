plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = BuildConfigConst.applicationId
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    api(project(":shared"))
    api(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // third parties
    implementation(libs.ktor.client.okhttp)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.timber)
}

object BuildConfigConst {
    const val applicationId = "com.pocket.customer"
    const val compileSdk = 34
    const val minSdk = 23
    const val targetSdk = 34

    private const val versionMajor = 2
    private const val versionMinor = 0
    private const val versionPatch = 8
    private const val versionBuild = 9
    const val appVersionName = "$versionMajor.$versionMinor.$versionPatch"
    const val appVersionCode = versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild

    const val devMinifyEnabled = false
    const val uatMinifyEnabled = false
    const val preprodMinifyEnabled = true
    const val prodMinifyEnabled = true

    const val devThreatCheck = false
    const val uatThreatCheck = false
    const val preprodThreatCheck = false
    const val prodThreatCheck = true
}