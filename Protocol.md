# 22-Dec-2022

* Now getting started on this project

* The Ceal Chronicler is supposed to be a tool that lets me keep track of characters, places, events and other things in my books and games

* Specifically, it should be able to answer questions such as:

  * Which character knows what, and when did they learn about that?
  * Which character is/was where at which point in time
  * Which character has which items/skills, etc..., and where/when did they get them

* Effectively, it is supposed to be a "viewport" for complex linked data, which also features a time axis

* It may at one point even include something like a map, to show how places are linked with one another

* But that is then

* Now, I am going to go step by step, implementing one functionality after the other

* The first part is going to be just setting up the project

* Since I want this to be my first proper multiplatform app, I have chosen Kotlin Multiplatform (KMP) to do it, and over the last week have also done some tutorials on that, so I actually know it is possible and have a working sample for an app that runs on both Windows and Android

* Anyway, I am now going to set up the project in Android Studio

  * The first question is going to be whether I want to keep the iOS components

    * I don't have any plans making the app iOS-worthy in the future, and neither do I have the hardware for it
    * On the other hand, the project wizard did simply add the components, and I know from earlier experience that removing them cleanly is nearly impossible for reasons which I can't understand
    * Also, I don't think they're doing any harm there, apart from bloating the repository size a little
    * So how about this: I'll keep them for the moment, and if they cause trouble sometime down the line, I'll remove them

  * Anyway, now the basic KMM app is set up

  * Setting up Desktop support is going to be a bit more complicated though

    * I'm going to refer back to this tutorial for that, and try to break it down:

      * https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/5-developing-ui-compose-multiplatform

    * I'll try to break down what needs to be done into a squence of steps here:

      1. Install the `Kotlin Multiplatform Mobile` and `Compose Multiplatform IDE Support` plugins in Android Studio

      2. Create a new `Kotlin Multiplatform App` project (under `Phone and Tablet`)

      3. In the `shared/build.gradle.kts`, in the `kotlin` block, after `android()`, add:

         - ````kotlin
           jvm("desktop"){
               compilations.all {
                   kotlinOptions.jvmTarget = "11"
               }
           }
           ````

      4. Add a new `desktop` folder at the top level of the app

      5. Add a new file `build.gradle.kts` into the `desktop` folder, and fill it with this content:

         - ````kotlin
           import org.jetbrains.compose.compose
           import org.jetbrains.compose.desktop.application.dsl.TargetFormat
           
           plugins {
               kotlin("multiplatform")
               id("org.jetbrains.compose") version "1.2.2"
           }
           
           group = "com.tri_tail.ceal_chronicler"
           version = "1.0.0"
           
           kotlin {
               jvm {
                   withJava()
                   compilations.all {
                       kotlinOptions.jvmTarget = "11"
                   }
               }
               sourceSets {
                   val jvmMain by getting {
                       kotlin.srcDirs("src/jvmMain/kotlin")
                       dependencies {
                           implementation(compose.desktop.currentOs)
                           api(compose.runtime)
                           api(compose.foundation)
                           api(compose.material)
                           api(compose.ui)
                           api(compose.materialIconsExtended)
           
                           implementation(project(":shared"))
                       }
                   }
               }
           }
           
           compose.desktop {
               application {
                   mainClass = "MainKt"
                   nativeDistributions {
                       targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                       packageName = "Ceal_Chronicler"
                       macOS {
                           bundleID = "com.tri_tail.ceal_chronicler"
                       }
                   }
               }
           }
           ````

      6. On the top level, in the `settings.gradle.kts` add `include(":desktop")` at the very end

      7. Click on "sync now" (Or `File > Sync project with gradle files`)

         - After the sync, the `desktop` folder should now be recognized as a module, indicated by a little square at the bottom right of the folder icon

      8. In the `desktop` module, create the folder chain `src/jvmMain/kotlin`

      9. In the just created `kotlin` folder create the Kotlin file `Main`, and fill it as follows:

         - ````kotlin
           import androidx.compose.foundation.layout.fillMaxSize
           import androidx.compose.material.Surface
           import androidx.compose.ui.Modifier
           import androidx.compose.ui.window.Window
           import androidx.compose.ui.window.application
           import androidx.compose.ui.window.rememberWindowState
           import androidx.compose.material.*
           
           fun main() {
               application {
                   val windowState = rememberWindowState()
           
                   Window(
                       onCloseRequest = ::exitApplication,
                       state = windowState,
                       title = "Ceal Chronicler"
                   ) {
                       Surface(modifier = Modifier.fillMaxSize()) {
                           Text(text = "Welcome to the Ceal Chronicler")
                       }
                   }
               }
           }
           ````

      10. Add a run configuration of type `Gradle` with the following settings:

          - **Name**: "Desktop"
          - **Run**: "run"
          - **Gradle Project:** "ceal-chronicler:desktop"

      11. If you started this from a KMM template, you will also have to create the file `shared/src/desktopMain/kotiln/com.tri_tail.ceal_chronicler/Platform.kt`, and fill it with this:

          - ``````kotlin
            package com.tri_tail.ceal_chronicler
            
            class DesktopPlatform : Platform {
                override val name: String = "Desktop"
            
            }
            
            actual fun getPlatform(): Platform = DesktopPlatform()
            ``````

      12. You should now be able to run the desktop app

  * Anyway, with that, I figure the basic project setup is complete

* Next, I want to create a common UI base for the Android and Desktop apps

  * It took some time, but there weren't really any problems along the way
  * Now I have a shared UI based on Compose that works on both the Desktop and the Android version
  * Next, I want to spend a bit of time polishing that shared view, on one hand so it looks nice, but on the other hand because I figure I can use some training with compose