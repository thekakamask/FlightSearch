plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dcac.flightsearch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dcac.flightsearch"
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
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Makes it easy to integrate Google Fonts directly into Jetpack Compose.
    implementation(libs.androidx.ui.text.google.fonts)
    //Provides additional Material Design icons for use in the user interface.
    implementation(libs.androidx.material.icons.extended)
    //Enables Jetpack Navigation to be used with Jetpack Compose to manage navigation between screens.
    implementation(libs.androidx.navigation.compose)
    //Facilitates integration of ViewModel into Jetpack Compose to manage states and lifecycle.
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Room is a Jetpack data persistence library.
    //It simplifies access to a SQLite database.
    //It allows you to structure data with entities (@Entity), access data via interfaces (@Dao) and define a base (@Database).
    //It generates safe, optimized code at compile time (thanks to KSP).
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //KSP for analyze and generate Kotlin code at compile time.
    //It is used by libraries that need to generate code automatically (such as Room, Hilt, Moshi...).
    ksp(libs.androidx.room.compiler)
    // Enables modern and asynchronous key-value data persistence using Preferences DataStore.
    implementation(libs.androidx.datastore.preferences)

}