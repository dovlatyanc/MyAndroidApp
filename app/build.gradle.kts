plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.devtoolsKsp)

    id("androidx.navigation.safeargs.kotlin")

}

android {


    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    namespace = "com.example.myapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.myapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {


        val apiKey = project.findProperty("WEATHER_API_KEY") as? String ?: ""

        debug {

            buildConfigField("String", "WEATHER_API_KEY", "\"$apiKey\"")
        }
        release {
            buildConfigField("String", "WEATHER_API_KEY", "\"$apiKey\"")
            isMinifyEnabled = true
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
}



dependencies {


    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)


    implementation(libs.androidx.navigation.fragment.ktx.v297)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity.ktx.v180)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.android)
    implementation(libs.exp4j)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.activity.ktx3)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}