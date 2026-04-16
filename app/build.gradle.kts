plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.train.trainingdemo"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.train.trainingdemo"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    //navigation
    val nav_version = "2.9.6"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    //live data
//    implementation("androidx.compose.runtime:runtime-livedata:1.9.2")

    implementation("com.google.dagger:hilt-android:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")
//
//    // Hilt Testing
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.56.2")
//    kspAndroidTest("com.google.dagger:hilt-android-compiler:2.56.2")
//
//    testImplementation("com.google.dagger:hilt-android-testing:2.56.2")
//    kspTest("com.google.dagger:hilt-android-compiler:2.56.2")


    //lotti
    implementation("com.airbnb.android:lottie-compose:6.6.9")
//    implementation(project(":splash-module"))
    implementation("org.osmdroid:osmdroid-android:6.1.16")

    //connect modules
//    implementation(project(":home-module"))
//    implementation(project(":search-module"))
    implementation(project(":splash"))
}