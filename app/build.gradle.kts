plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.app.classportal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.classportal"
        minSdk = 29
        targetSdk = 34
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
}

dependencies {
    implementation ("androidx.compose.material3:material3:1.0.0-alpha01")
    implementation ("androidx.compose.material:material:1.1.1")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha10")
    implementation ("com.google.accompanist:accompanist-pager:0.21.2-beta")
    implementation ("androidx.work:work-runtime-ktx:2.7.1")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.compose.ui:ui:1.6.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation ("com.google.accompanist:accompanist-pager:0.25.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.25.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation ("androidx.compose.material:material:1.3.0-alpha02")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.1")
    implementation ("com.google.code.gson:gson:2.8.6")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material:material-icons-extended-android:1.6.7")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}