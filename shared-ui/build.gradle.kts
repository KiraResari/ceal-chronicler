plugins {
    kotlin(Dependencies.Plugins.kotlinMultiplatform)
    id(Dependencies.Plugins.androidLibrary)
    id(Dependencies.Plugins.compose).version(Versions.compose)
    id("dev.icerock.mobile.multiplatform-resources").version("0.20.1")
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

                api("dev.icerock.moko:resources:0.20.1")
                api("dev.icerock.moko:resources-compose:0.20.1")
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api(Dependencies.Compose.runtime)
                api(Dependencies.Compose.ui)
            }
        }
        val desktopMain by getting
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.tri_tail.ceal_chronicler"
}
