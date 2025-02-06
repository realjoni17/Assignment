plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
   id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.joni.assignment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.joni.assignment"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
   /* buildFeatures{
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }*/
   kapt {
        correctErrorTypes = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.datastore.preferences) //datastore
    implementation (libs.hilt.android) //Hilt
    kapt (libs.hilt.compiler) //Hilt Compiler
   implementation (libs.retrofit) //Retrofit
  // implementation(libs.coil.compose)  //Coil
    implementation(libs.logging.interceptor)  //Logging Interceptor
    implementation (libs.converter.gson)  //Gson
   // implementation (libs.compose)  //Glide
    implementation(libs.coil.kt.coil.compose)
    implementation (libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json) // Serialization library
    implementation(libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
}