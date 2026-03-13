plugins {
    alias(libs.plugins.android.application)
    id ("androidx.navigation.safeargs")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.tengiz.itstepacademy_finalproject_android"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.tengiz.itstepacademy_finalproject_android"
        minSdk = 26
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.security.crypto)

    implementation(libs.androidx.swiperefreshlayout)

    //card view
    implementation(libs.androidx.cardview)
    //recyclerview
    implementation(libs.androidx.recyclerview)

    //Dagger - Hilt
    implementation(libs.hilt.android)

    ksp(libs.hilt.android.compiler)

    //moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)

    //glide
    implementation (libs.glide)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}