plugins {
    kotlin(Dependencies.Plugins.kotlinMultiplatform)
    id(Dependencies.Plugins.androidLibrary)
    id(Dependencies.Plugins.compose).version(Versions.compose)
    id(Dependencies.Plugins.icerockMultiplatformResources)
        .version(Versions.icerockResources)
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
                implementation(Dependencies.eventBus)

                api(compose.foundation)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.ui)
                api(compose.uiTooling)

                api(Dependencies.Moko.resources)
                api(Dependencies.Moko.resourcesCompose)
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
    multiplatformResourcesPackage = Namespaces.cealChronicler
}
