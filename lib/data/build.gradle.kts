import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("kotlin-kapt")
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.test.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

dependencies {

    implementation(project(":lib:domain"))
    implementation(project(":lib:network"))

    implementation(libs.hilt.android)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.network.retrofit.core)
    implementation(libs.network.retrofit.scalars)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)

    implementation(libs.kotlin.test)

    kapt(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mock.server)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}