plugins {
    id(Dependencies.Plugins.androidApplication)
    kotlin(Dependencies.Plugins.kotlinAndroid)
}

android {
    namespace = Namespaces.cealChroniclerAndroid
    compileSdk = Versions.sdk
    defaultConfig {
        applicationId = Namespaces.cealChroniclerAndroid
        minSdk = Versions.sdk
        targetSdk = Versions.sdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.androidCompose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":shared-ui"))
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiTooling)
    implementation(Dependencies.Compose.foundation)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.androidCompose)
}