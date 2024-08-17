plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.googleService)
    alias(libs.plugins.crashlytics)
}

android {
    namespace = BuildConfigConst.APPLICATION_ID
    compileSdk = BuildConfigConst.COMPILE_SDK

    defaultConfig {
        applicationId = BuildConfigConst.APPLICATION_ID
        minSdk = BuildConfigConst.MIN_SDK
        targetSdk = BuildConfigConst.TARGET_SDK
        versionCode = BuildConfigConst.APP_VERSION_CODE
        versionName = BuildConfigConst.APP_VERSION_NAME

        setProperty("archivesBaseName", "ComposeMovieApp-$versionName")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(rootProject.ext["credentialStoreFile"] as String)
            storePassword = rootProject.ext["credentialStorePassword"] as String
            keyAlias = rootProject.ext["credentialKeyAlias"] as String
            keyPassword = rootProject.ext["credentialKeyPassword"] as String
            enableV2Signing = true
        }

        getByName("debug") {
            storeFile = file(rootProject.ext["credentialStoreFile"] as String)
            storePassword = rootProject.ext["credentialStorePassword"] as String
            keyAlias = rootProject.ext["credentialKeyAlias"] as String
            keyPassword = rootProject.ext["credentialKeyPassword"] as String
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = BuildConfigConst.DEV_MINIFY_ENABLED
            isShrinkResources = BuildConfigConst.DEV_MINIFY_ENABLED
            isDebuggable = !BuildConfigConst.DEV_MINIFY_ENABLED

//            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "[Dev] ComposeMoviesApp")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = BuildConfigConst.PROD_MINIFY_ENABLED
            isShrinkResources = BuildConfigConst.PROD_MINIFY_ENABLED
            isDebuggable = !BuildConfigConst.PROD_MINIFY_ENABLED

            resValue("string", "app_name", "Compose MoviesApp")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":appbase"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.activity)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.androidx.compose.animation.core)
    implementation(libs.androidx.compose.animation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // third parties
    implementation(libs.compose.viewmodel)
    implementation(libs.timber)
    implementation(libs.coil)
    implementation(libs.bundles.paging)
    implementation(libs.constraint.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
}

object BuildConfigConst {
    const val APPLICATION_ID = "com.pthw.composemovieappcleanarchitecture"
    const val COMPILE_SDK = 34
    const val MIN_SDK = 23
    const val TARGET_SDK = 34

    private const val VERSION_MAJOR = 1
    private const val VERSION_MINOR = 0
    private const val VERSION_PATCH = 0
    private const val VERSION_BUILD = 2
    const val APP_VERSION_NAME = "$VERSION_MAJOR.$VERSION_MINOR.$VERSION_PATCH"
    const val APP_VERSION_CODE = VERSION_MAJOR * 1000000 + VERSION_MINOR * 10000 + VERSION_PATCH * 100 + VERSION_BUILD

    const val DEV_MINIFY_ENABLED = false
    const val UAT_MINIFY_ENABLED = false
    const val PREPROD_MINIFY_ENABLED = true
    const val PROD_MINIFY_ENABLED = true
}