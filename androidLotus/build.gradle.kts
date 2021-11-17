plugins {
    id(Plugins.androidApplication)
    kotlin(KotlinPlugins.android)
    kotlin(KotlinPlugins.kapt)
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(Plugins.hilt)
}
android {

    compileSdk = Application.compileSdk
    defaultConfig {
        applicationId = Application.appId
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}
dependencies {
    implementation(project(":shared"))
    implementation(AndroidX.appCompat)
    implementation(Compose.runtime)
    implementation(Compose.runtimeLiveData)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.uiTooling)
    implementation(Compose.foundation)
    implementation(Compose.compiler)
    implementation(Compose.constraintLayout)
    implementation(Compose.activity)
    implementation(Compose.navigation)
    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltNavigation)
    implementation(Ktor.android)
    implementation(Hilt.hiltNavigationCompose)
    kapt(Hilt.hiltCompiler)
    implementation(Google.material)
    implementation(Kotlinx.datetime)
    debugImplementation(SquareUp.leakCanary)

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
}