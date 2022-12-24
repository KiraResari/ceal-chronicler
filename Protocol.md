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
    * I now did that too, and at this point I have to note that so far it feels like the Compose features are a lot like what I got used to in Rust last year, but with less lifecycle horror
  * While playing around with that, I also cane across this documentation, which I imagine might come in handy:
    * https://developer.android.com/jetpack/compose/documentation

* This is as far as I'm getting with this today



# 23-Dec-2022

* Now continuing with this

* Last time, I managed to set up the project, and got it running to the point where I now have a shared UI between Android and Desktop that displays a little welcome message

* I have to point out again that starting the project takes time every day =>,<=

  * As in, it takes Kotlin/Android studio several minutes to load the project, which makes the whole framework feel a bit sluggish

* Oh, good thing I checked, because I just noticed the android app is no longer starting

  * The error that happens is:

    * ````
        1.  Dependency 'androidx.compose.ui:ui-graphics:1.3.2' requires libraries and applications that
            depend on it to compile against version 33 or later of the
            Android APIs.
        
            :androidApp is currently compiled against android-32.
        
            Recommended action: Update this project to use a newer compileSdkVersion
            of at least 33, for example 33.
      ````

    * I now adjusted the `compileSdk`, `minSdk` and `targetSdk` in the `androidApp/build.gradle.kts` to "33"

  * Looks like that fixed it

* Now, next I want to improve the title screen by adding the logo that I prepared

  * That won't do much for the functionality, but I just know it'll work wonders for my motivation

  * Also, this way I'll learn how to integrate pictures

  * This might be a good place to start for this:

    * https://developer.android.com/jetpack/compose/graphics/images/loading
    * Hmm, unfortunately, that's not really helpful

  * Maybe this will be better?

    * https://developer.android.com/codelabs/basic-android-kotlin-compose-add-images#3
    * Okay, based on that, I _think_ the image needs to go in `ceal-chronicler\shared-ui\res\drawables`
    * Hmm, that doesn't really work either

  * The theory is all well and good, but in practice it appears there are problems with the fact that I'm not on android, and as such the `R` class does not seem to exist, meaning this claim does not apply:

    * > An `R` class is an automatically generated class by Android that contains the IDs of all resources in the project

  * Or am I missing a dependency somewhere?

  * Maybe this full example here will help?

    * https://github.com/Foso/Jetpack-Compose-Playground/blob/master/app/src/main/java/de/jensklingenberg/jetpackcomposeplayground/mysamples/github/foundation/ImageResourceDemo.kt
    * Not really...

  * Maybe this will help?

    * https://luisramos.dev/how-to-share-resources-kmm
    * That seems horrible! No!

  * How about this plugin?

    * https://github.com/icerockdev/moko-resources
    * Nope, I'm not making any progress there either
    * I have now written a help request about that here:
      * https://github.com/icerockdev/moko-resources/issues/398

  * Ugggh! This is absolutely infuriating! Adding pictures should be one of the simplest things there is in app design! Seeing it be this complicated and unintuitive does not bode well at all

  * Next try. Will this help?

    * https://nrobir.medium.com/uploading-images-in-kotlin-multiplatform-ecf87e866505

    * Hmm, nope, that only seems to be for images that are uploaded to the app, which is different from what I need

    * But let me have a closer look at the source code here because he also mentioned app icons:

      * https://github.com/halcyonmobile/MultiplatformPlayground

      * Hmm, it would seem that one uses a plugin called "dev.icerock.mobile.multiplatform-resources", which is somewhat, but not quite the same as the moko-Plugin that I just tried to get to run

      * I also note that it features that `MR` folder for resources that was mentioned in the other thing

      * I tried implementing that, but now I get this error:

        * ````
          E:\projects\ceal-chronicler\shared-ui\src\main\AndroidManifest.xml (Das System kann die angegebene Datei nicht finden)
          ````

        * ...why? Just why?

        * Why is it looking for a file like that now, and why there? This has nothing to do with anything, and I never asked it to look for that file there, and it doesn't belong there either

        * Why?

        * WHYYYYYYYYYYYYYYYY????????????????????

      * The problem is that this project, and literally ALL other projects are laid out completely different than mine

      * But then maybe that's the problem

      * Let me check out that project and see if it runs for me

        * Well, for starters, I had to upgrade the project since it was incompatible with my Android Studio
        * And that's taking literally forever =>,<=

      * Okay, so scratch that, this one does not feature desktop =>,<=

  * Meanwhile, I'm having a look at this:

    * https://jamiecraane.dev/2021/07/27/resource_images_kmm/
    * Uggh, no! That's also *way* too complicated =>,<=

* I need a break

* Let me just try to set up the `buildSrc` module now, because I've seen that in a number of projects now, and I think it's a good thing to have, and I feel like I can actually do that by myself

  * And even this is turning into a nightmare

  * This sucks! I am at the very beginning of the project, and yet it feels like I've already completely lost control over it =>,<=

  * Now I get this error message:

    * ````
      Caused by: org.gradle.api.internal.artifacts.ivyservice.DefaultLenientConfiguration$ArtifactResolveException: Could not resolve all files for configuration ':androidApp:debugRuntimeClasspath'.
      ````

    * Why?

    * ```
      org.gradle.internal.resolve.ModuleVersionNotFoundException: Could not find androidx.compose.ui:ui-graphics:1.2.2.
      ```

    * Could there be two compose versions?

    * I think in my original project I had 1.2.2 and 1.3.0 mixed for some reason

    * so, just checked, for `androidx.compose.ui:ui-graphic`, the correct version would be 1.3.2

    * Next is this:

      * ````
        org.gradle.internal.resolve.ModuleVersionNotFoundException: Could not find androidx.compose.foundation:foundation:1.3.2.
        ````

      * That one only goes up to 1.3.1

      * So let's just make all the compose things 1.3.1 for better compatability

  * Okay, so after way more trouble than anticipated (again), I have now finished this, and everything seems to work alright again

* That means that now I'm back with the image problem again

  * ...I am not going to work on that again today
  * Instead, I now posted a help request about this here:
    * https://stackoverflow.com/questions/74902818/kotlin-multiplatform-add-images-on-android-and-desktop

* This is as far as I'm getting with this today



# 24-Dec-2022

* Now continuing with this
* Yesterday I spent all day trying to get an image to work and failed miserably
  * The help request I posted about that didn't get an answer yet
* While this is annoying and possibly a killer criterion, I am not going to let it stop me at this point, because there's still a bunch of things that I want to try out in Kotlin Multiplatform
* So I'm going to carry on as best as I can
* Next, I want to implement characters
  * Two different approaches come to mind
    * I could now try to lay out the entire structure of the app in advance as I see it and implement everything I need step by step, such as a main menu, a character selector and a character view
    * Or, I could just start with the character view and add extra screens as they become necessary
  * I think I'm gonna go for the second option, since that reflects how I want to program
    * Specifically, I want to see if both I and the framework can make it so that the app is easy to restructure at a later date
    * That is, I will try to design the architecture in such a way that it is easy to put, for example, a character selector and a main menu between the character and the start screen later on, and in doing so I will learn how well the framework supports this approach
  * Following that basic idea of adaptable and emergent design, I'll try to implement only the things that are absolutely necessary for each step to work, while trying to keep the design as flexible as possible for later additions
  * So, basically, a `Character` class with hard-coded values is all I need for this, which I feel also makes sense for now, because the current challenge is going to get the UI to display a character view. I can worry about how to adjust characters once I got this working
  * The first question is how the view switching works
    * The tutorial I did earlier did that, so I should be able to copycat it from there



# ⚓



# Kotlin Multiplatform Pros & Cons

## Pros

* You can use Kotlin Compose to create frontends that will work on both Android and Desktop, including iOS Desktop
  * For iOS Mobile, you need frontend code written in Swift though (as far as I can tell)

## Cons

* Long build times
  * 10-minute waits on project sync-ups
  * 2-minute wait on daily start-up
  * Regular wait times during project builds and gradle refreshes

* I'm having considerable trouble even just with the Tutorials, which makes me not at all optimisitic about building an actual production app with this
* Setting up a project seems overly complicated
  * Regularly getting project configuration issues, such as modules not being recognized as such, and it apparently is impossible to tell the IDE that a folder is a module

* Large file sizes (129 MB for a program that barely does anything)

* Multiplatform support for resources is not natively implemented



# Benchmarks

## IDE

* Overall: Good (++)
* (+) The IDE is Android Studio, and it works well
* (+) Refactoring, code navigation and syntax highlighting all work without problems
  * Refactoring hasn't been thoroughly tested yet though

## Project setup

* Overall: Bad (--)
* (-) A Kotlin Multiplatform Mobile project is set up easily via wizard, but a true multiplatform project that also supports desktop needs to be done manually
* (-) At the end, you end up with a Moloch with six modules and just as many `build.gradle.kts` files, which are already full of dependencies
  * Put simply: You *start* already with dependency hell

## Multiplatform support

* Overall: Kinda bad (-)
* It fells like it's half-baked yet
* (-) Android and Desktop can share the same frontend code, but for iOS you need to program in Swift
  * (-) And even in the Android and Desktop shared frontend code, there are some problems, such as shared resources not really working
* (+) Backend code can be shared across all projects

## Dependency Injection

* Not tested yet

## Persistence

* Not tested yet

## Testing

* Not tested yet

## Project load times

* Overall: Very Bad (---)
* (-) When setting up a project or loading a project for the first time, it takes 10+ minutes for the initial gradle build
* (-) When loading a project after starting the IDE, it still takes about 2 minutes to get ready
* (-) Further minute-long load times whenever any build.gradle is adjusted

## File Size

* Overall: Very Bad (---)
* (---) even a very basic program with no media is already 129MB big, which is roughly three orders of magnitude above what I'd expect
  * For contrast, that's over twice as big as the maximum size that Nintendo 64 games could be 

# Knowledgebase

## Bugfixes

### Module is not recognized

**Symptoms**

* A folder that is supposed to be a module is not recognized as such

**Cause**

* Possibly a typo
* For a folder to be recognized as a module, the `settings.gradle.kts` has to have an `include` statement for that folder
* If either the module folder or the include statement  have a typo, then the module will not be recognized
* Example:
  * Folder: `desktop`
  * Include statement: `include(":dektop")`⚡

**Fix**

* Make sure the module folder name and the module import statement are the same and have no typos

# 