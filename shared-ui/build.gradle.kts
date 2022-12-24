plugins {
    kotlin(Dependencies.Plugins.kotlinMultiplatform)
    id(Dependencies.Plugins.androidLibrary)
    id(Dependencies.Plugins.compose).version(Versions.compose)
}

android {
    namespace = Namespaces.cealChroniclerAndroid
    compileSdk =  Versions.sdk
    defaultConfig {
        minSdk = Versions.sdk
        targetSdk = Versions.sdk
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
dependencies {
    implementation(Dependencies.Compose.uiGraphics)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.material_desktop)
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = Versions.jvm
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))

                api(compose.foundation)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.ui)
                api(compose.uiTooling)
                api(compose.preview)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
            }
        }
        val desktopMain by getting
    }
}