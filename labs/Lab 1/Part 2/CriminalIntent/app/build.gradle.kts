plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("androidx.navigation.safeargs") // ✅ Use this (not `safeargs.kotlin`)
}

android {
    namespace = "com.bignerdranch.android.criminalintent"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bignerdranch.android.criminalintent"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true // Enable Data Binding
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    implementation("androidx.room:room-runtime:2.5.1")
//    kapt("androidx.room:room-compiler:2.5.1")
//    implementation("androidx.room:room-ktx:2.5.1") //

    implementation("androidx.core:core-ktx:1.12.0") // Updated from 1.7.0
    implementation("androidx.appcompat:appcompat:1.6.1") // Updated from 1.4.1
    implementation("com.google.android.material:material:1.11.0") // Updated from 1.5.0
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Minor update from 2.1.3
    implementation("androidx.fragment:fragment-ktx:1.6.2") // Updated from 1.4.1
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0") // Updated from 2.4.1
    implementation("androidx.recyclerview:recyclerview:1.3.2") // Updated from 1.2.1
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // Updated from 1.6.0
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Updated from 1.6.0
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") // Updated from 2.4.1
    implementation("androidx.room:room-runtime:2.6.1") // Updated from 2.4.2
    implementation("androidx.room:room-ktx:2.6.1") // Updated from 2.4.2
    kapt("androidx.room:room-compiler:2.6.1") // Updated from 2.4.2
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6") // Updated from 2.4.1
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6") // Updated from 2.4.1
    testImplementation("junit:junit:4.13.2") // No change needed
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Updated from 1.1.3
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Updated from 3.4.0
}