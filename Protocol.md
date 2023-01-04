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

      * I think the gist of that is:

        * ````
          val selectedIndex = remember { mutableStateOf(0) }
          [...]
                      when (selectedIndex.value) {
                          0 -> TimeZoneScreen(currentTimezoneStrings)
                           1 -> FindMeetingScreen(currentTimezoneStrings)
                      }
          ````

      * I wonder if that will also work with an enum

    * But based on that, I figure what I need to do is make what I have now into something like a `TitleScreen`, and then give that a button that changes the `selectedIndex` (I'll want to give that a better name, like `activeScreen`)

    * Okay, looks like that basically works, and with an Enum too

    * I now successfully extracted the `TitleScreen` and gave the `MainView` an `MainViewState` variable that is remembered and currently causes the title screen to be drawn

    * Next, I want to implement the character screen and see if that is displayed instead if I manually switch the state (and if that works, I'll next try to add a button to switch the state in-app)

      * While trying to do that, I wanted to try out the Preview feature of Compose, but for some reason, even though I added the dependency for the Preview feature, it doesn't recognize it

        * ````
          e: E:\projects\ceal-chronicler\shared-ui\src\commonMain\kotlin\com\tri_tail\ceal_chronicler\ui\character_screen\CharacterScreen.kt: (15, 36): Unresolved reference: preview
          ````

        * I now created this help request for that:

          * https://stackoverflow.com/questions/74908124/kotlin-compose-preview-reference-unresolved

        * Meanwhile, I suppose this means that I can't use previews either

      * Anyway, I now managed to implement the character screen

    * Next, I want to implement a button on the Title Screen that takes me to the Character Screen

      * That might be a bit interesting since that means I somehow have to adjust the `mainViewState` variable on the `MainView` from the `TitleScreen`

        * Yeeees, this turn out to be a problem

        * I've tried out a number of things, such as passing `mainViewState` or `mainView.State.value` into the `TitleScreen` function, and they all give me this error:

          * ````
            Val cannot be reassigned
            ````

        * Ah, I think like this it works:

          * ````
            @Composable
            fun MainView() {
                val mainViewState = remember { mutableStateOf(MainViewState.CHARACTER) }
                AppTheme {
                    when(mainViewState.value){
                        MainViewState.TITLE -> TitleScreen(mainViewState)
                        MainViewState.CHARACTER -> CharacterScreen()
                    }
                }
            }
            
            @Composable
            fun TitleScreen(mainViewState: MutableState<MainViewState>) {
                [...]
                        Button(onClick = {
                            mainViewState.value = MainViewState.CHARACTER
                        }) {
                            Text(text = "Go to Character Screen")
                        }
                    }
                }
            }
            ````

        * Yes it does! Yes it does! Yes it does! =^,^=

* Meanwhile, one of the help requists regarding the pictures has triggered, and the person even made a PR for that, so let's see

  * Regardless of whether it will ultimately work or not, it's in no way simple or straightforward, since the PR only included a sample for a string resource and not an image, and I had to resolve conflicts
  * But the string resource seems to work, that's at least one good thing
  * Anyway, I made a non-working version of how I imagine it should work, and ponged it back to him on that branch
  * Meanwhile, I will continue with non-image stuff

* The next thing I want to implement is multiple characters, and a way to select them

  * This requires at least two things:
    * Some sort of `CharacterRepository` where all the characters are stored
    * A `CharacterSelectionScreen` or something

  * I now managed to do that

* With that, I feel like the program is at this point sufficiently complex that it makes sense to address Dependency Injection and Persistence next

* I'd also like to note that I don't feel entirely happy with the structure that I feel Compose dictates

  * Currently, the structure is very hierarchical: A main view that contains a title screen and character selector, which in turn contains characters, and I always have to pass on the "control variables" if I want to be able to control things from the next layer
  * What I'd like, envisioned very roughly, would be to have each view or screen in a class, and those be able to fire events like `OpenCharacterScreen(id)` or `OpenMainMenu()` that would cause that view to pop up
  * Basically, I think what I want is the theoretical option to go from every view in my app to every other view while retaining the overall state
  * I wonder if that's possible, or rather, if it's possible without disproportionally large effort

* Anyway, I won't be doing any big things in what's left of today, but I still have some time, so I figure I might as well use that to beautify the app a bit 

  * I now did that

* And this is as far as I'm getting with this today



# 26-Dec-2022

* Now continuing with this

* There was some feedback on the Image situation, so I will try to address that first

  * Oh my, looks like it works now! Awesome!
  * Now I'll just have to see what changed, and possibly write it down 

* I'll now do some cleanup in the wake of the merged PR that got the images to work

  * For one, they hard-coded the versions into the `build.gradle.kts` file, and I want to keep them consistently within my chosen approach of having all those things stored in the `Dependencies.kt`

  * I also want to try out if it works with the latest version of the Android Gradle Plugin

    * Looks like it doesn't

    * On Desktop it works, but on Android, it throws this error in one of its generated files

      * ````
        e: E:\projects\ceal-chronicler\shared-ui\build\generated\moko\androidMain\src\com\tri_tail\ceal_chronicler\MR.kt: (16, 70): Unresolved reference: string
        ````

    * Oh well, I suppose the old android version will do

  * Finally, there's that issue of the moko `stringResource` not being recognized in the IDE, which displays me this error:

    * ````
      Unresolved reference: stringResource
      ````

    * I now tried adding `implementation(Dependencies.Moko.resourcesCompose)` to the Dependencies of `shared-ui`

      * But that didn't change anything

    * I also tried `implementation(Dependencies.Moko.resources)`

      * But that didn't do anything either

    * So, how come this works when I compile it?

    * Do we even need it?

      * Yes we do, because otherwise the project complains that I am handing it a `StringResource` where it needs a `String` (which is kinda confusing, since I just removed the `stringResource(...)`), but okay
      * But maybe it works if I simply affix `toString()` instead?
      * Mmmh, no, while this is syntactically correct, it does produce a String-representation of the `StringResource` instance instead of giving me its value there 

    * Okay, so I now posted a help request here, and will now move on to something else:

      * https://github.com/icerockdev/moko-resources/issues/401

* Now to the actual thing that I wanted to do today

* I have been noticing that the convenience of the project is starting to derail

  * I've got a multi-layered frontend where frontend functions are passing on their state variables to the functions they call so that these functions can modify the  state of the previous functions
  * That seems like a recipe for disaster, and I can see it growing more unstable and unmaintainable as the project grows in complexity  
  * I do not want to indulge in that
  * So, I need to come up with a better project structure
  * Ideally, I want the backend to tell the frontend what to it should draw on the screen, and the frontend do nothing more than draw and throw events which are listened to by the backend
    * So basically, I want the frontend to be truly stateless, and not feature _any_ `remember` variables (except maybe for input fields where the entered values are sent via event as soon as some sort of submit button is clicked)
    * Is that feasibly possible?
    * To answer that, I think I need to better understand the project structure, because thus far I've largely been copycatting, and am still working on getting a profound understanding of what all this adjusted sample code is actually doing
      * Thus far, I've regarded the `MainView.kt` as the entry point of the program, but that's not actually true, is it?
        * No, the actual entry points are `com.tri_tail.ceal_chronicler.android.MainActivity` for Android and the `main()` function from `src/jvmMain/kotlin/Main.kt`
        * That is moderately good
        * The solution for Android is actually what I'd prefer, and what I would have known how to work from
        * However, the Desktop solution is kinda bogus, and I don't know what to do with that
        * Since this is also sorta related to dependency injection, let me have a look at the files from the Dependency Injection tutorial
          * The Tutorial itself is behind a paywall, but fortunately, the sample files themselves are perfectly useable
            * https://github.com/kodecocodes/kmpf-materials/
          * And fortunately, since the repository features both a "start" and a "final" version for each chapter, it is also easy to see what changed by copying the contents of "final" over "start", and then using git to view the differences
          * Okay, so...
          * ...I am not entirely in the clear about what is happening there, but the basic structure of the Desktop app being basically a function call while the Android App is in a class doesn't seem to change
          * Also, in place of Spring (as I would have expected), somethin called "Koin" seems to be used for dependency injection there
          * Also, the IDE does not seem to be comfortable with that, which may or may not be related on a missing plugin
          * Anyway, I don't want to do Dependency Injection just yet, so let's leave that for later
          * Meanwhile, thinking about that I did get some ideas for how this might work, _assuming_ that the application does work how I think it does
          * While I still don't quite understand where the main loop happens on Desktop, it _should_ work if I simply wrap the call to `MainView` in a common class which both the Android an Desktop apps instantiate
          * If that works, I can make that class into an Event Listener then, and change it to draw the appropriate view  
            * Looks like that works, although I do have to note that I had to put the `ViewController` into `shared-ui` to make it work because otherwise it  could not access the `MainView`
              * I suppose that makes sense, though, since the `ViewController` is effectively in charge of the view, and nothing else
              * I'll have to keep that in mind for sequencing though, since that means that the `ViewController` should not be allowed to update the models (again, this makes perfect sense, now that I think about it, and I actually like it how the structure that is starting to emerge here lets me see such things)
                * That means that one possible sequencing for, say, updating a character might be like `UpdateCharacterEvent` > `CharacterModel.UpdateCharacter` > `UpdateCharacterViewEvent` > `ViewController.UpdateView`
  
* Okay, so now that I have the basic groundwork in place, and a better idea of what I'm doing, let's proceed with he events

  * Currently, I think I need two events: `OpenCharacterSelectionViewEvent` and `OpenCharacterViewEvent`, both of which can inherit from a superclass `OpenViewEvent`

  * I think it's gonna be best if those are in the `shared` module, since classes from both `shared` and `shared-ui` will have to respond to them, and I think I already established that the `shared-ui` module depends on the `shared` module

  * But before I can actually do that, I think I need to read up on Events in Kotlin

    * Uhh, I am a bit taken aback by the fact that this is apparently not as straightforward as I would have assumed

      * Maybe this will provide me with more insight?

        * https://dev.to/mohitrajput987/event-bus-pattern-in-android-using-kotlin-flows-la

        * Wow, talk about recent! That article was literally published yesterday!

        * However, I am having trouble implementing it

          * For one, the project can't find `MutableSharedFlow`

          * I tried adding `implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")` to the dependencies of the `shared` module

            * However, while that does run through the gradle sync, it does not solve the problem

            * Specifically, the import `import kotlinx.coroutines.flow` does not work afterwards (specifically, the `flow` part seems to be the problem)

            * That is so weird since the documentation says that it is part of `kotlinx-coroutines-core/kotlinx.coroutines.flow/MutableSharedFlow`

            * Maybe I'm using the dependencies wrong...

            * The `build.gradle.kts` for the `shared` module is looking kinda funky anyways:

              * ``````kotlin
                plugins {
                    kotlin(Dependencies.Plugins.kotlinMultiplatform)
                    id(Dependencies.Plugins.androidLibrary)
                }
                
                kotlin {
                    android()
                
                    jvm("desktop"){
                        compilations.all {
                            kotlinOptions.jvmTarget = "11"
                        }
                    }
                    
                    listOf(
                        iosX64(),
                        iosArm64(),
                        iosSimulatorArm64()
                    ).forEach {
                        it.binaries.framework {
                            baseName = "shared"
                        }
                    }
                
                    sourceSets {
                        val commonMain by getting
                        val commonTest by getting {
                            dependencies {
                                implementation(kotlin("test"))
                            }
                        }
                        val androidMain by getting
                        val androidTest by getting
                        val iosX64Main by getting
                        val iosArm64Main by getting
                        val iosSimulatorArm64Main by getting
                        val iosMain by creating {
                            dependsOn(commonMain)
                            iosX64Main.dependsOn(this)
                            iosArm64Main.dependsOn(this)
                            iosSimulatorArm64Main.dependsOn(this)
                        }
                        val iosX64Test by getting
                        val iosArm64Test by getting
                        val iosSimulatorArm64Test by getting
                        val iosTest by creating {
                            dependsOn(commonTest)
                            iosX64Test.dependsOn(this)
                            iosArm64Test.dependsOn(this)
                            iosSimulatorArm64Test.dependsOn(this)
                        }
                    }
                }
                
                android {
                    namespace = "com.tri_tail.ceal_chronicler"
                    compileSdk = 32
                    defaultConfig {
                        minSdk = 21
                        targetSdk = 32
                    }
                }
                ``````

              * It doesn't even have a `dependencies` block to begin with (though I can add one without any problems)

              * The sample project seems to handle dependencies like this:

                * ````kotlin
                  kotlin {
                      [...]
                      sourceSets {
                          val commonMain by getting {
                              dependencies {
                                  implementation(Deps.JetBrains.datetime)
                  
                                  implementation(Deps.napier)
                              }
                          }
                          val commonTest by getting {
                              dependencies {
                                  implementation(kotlin("test-common"))
                                  implementation(kotlin("test-annotations-common"))
                              }
                          }
                          val androidMain by getting
                          val androidTest by getting {
                              dependencies {
                                  implementation(kotlin("test-junit"))
                                  implementation("junit:junit:4.13.2")
                              }
                          }
                  
                          // Set up dependencies between the source sets
                          val iosMain by getting {
                              dependsOn(commonMain)
                          }
                          val iosTest by getting
                          val iosSimulatorArm64Main by getting
                          val iosSimulatorArm64Test by getting
                          iosSimulatorArm64Main.dependsOn(iosMain)
                          iosSimulatorArm64Test.dependsOn(iosTest)
                      }
                  }
                  ````

              * That's kinda funky, but let's try if I can get it to work like that

              * Yes, looks like doing it like this works:

                * ````kotlin
                  kotlin {
                      [...]
                      sourceSets {
                          val commonMain by getting {
                              dependencies {
                                  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                              }
                          }
                          [...]
                      }
                  }
                  ````

              * However, after trying to implement it, I eventually arrived at this error:

                * ````
                  Suspend function 'publish' should be called only from a coroutine or another suspend function
                  ````

                * Well, that sucks

                * I am not deep enough into what that means to really be sure, but my guess it that this will prevent me from firing events from the frontend, which is something that I need to do

                * So I don't think this will work after all

  * Maybe this will work instead?

    * https://github.com/DevSrSouza/EventKt
    * Hmm, no, that has not been maintained for over 2 years, and is only downloadable via an insecure protocol

  * Maybe this?

    * https://github.com/greenrobot/EventBus

    * Yes! This! This is what an event system should be looking like!

    * Now let's see if I can integrate it into my project

    * My main concern is that this is Java, but I do recall reading that all valid Java is valid Kotlin or something

    * Mmmh, doesn't look like it's working yet, however...

    * I got it to start up without errors, but now nothing happens when I click on the `To Character Selection Screen`-button

    * Debugging has shown that the `ViewController` registers itself in the `EventBus` with ID 2526, and the event also gets posted to that same `EventBus` instance, which I can see has the `ViewController` as one of its subscribers

    * And I see that the `mainViewState` variable also gets updated, but the `draw` method is not being called again

    * And I can't just re-call `draw` here because the observing method is not `@Composable`

      * In fact, if I try to make it `@Composable`, the project build fails with this error:

        * ````
          Execution failed for task ':desktop:run'.
          > Process 'command 'C:\Programs\Android Studio\jre\bin\java.exe'' finished with non-zero exit value 1
          ````

    * Maybe I need to do it with one of those weird `MutableState` objects after all?

      * Nope, doesn't work either =>,<=

    * That sucks!

    * This sucks!!!

    * Okay, well, maybe I need to do some tricks with dependency injection here after all 

    * But before I go down that particular rabbit hole, I'll try posting another help request

      * https://stackoverflow.com/questions/74922830/kotlin-multiplatform-compose-how-to-use-events-to-control-views

* This is as far as I'm getting with this today



# 28-Dec-2022

* Now continuing with this

* Today I only have a little time left, but let's see where I can get with that

* Last time, I got stuck trying to get the frontend to respond to events

  * I sent a help request to that, and now I got a reply there

  * That one linked me to here, which does seem worth a look:

    * https://github.com/adeo-opensource/kviewmodel--mpp
    * For one, that's definitely not straightforward, and will probably require extensive remodeling
    * But since I don't have much yet, "extensive" is not actually all that much
    * The more interesting question is if it will work
    * The code samples on the page don't really tell me much, so I have checked out the repository and am gonna look at the compose sample project
      * Unfortunately, that does not really work, and is not n-complete, so I don't think I can learn much from that
    * The post also mentioned something called "MVI Architecture", and the plugin mentions that alongside MVVM, so maybe I should look into those

  * So, here's a tutorial for MVI:

    * https://blog.mindorks.com/mvi-architecture-android-tutorial-for-beginners-step-by-step-guide/
    * Hmm, looks interesting, but the question if it that will work with Compose
    * For all purposes, it seems like this is more or less the same that I want to do with events
    * It does seem to be using the Flows instead of events though, so maybe that's the way to go

  * I now found this tutorial for MVI with Compose, so let's have a look at that:

    * https://medium.com/@VolodymyrSch/android-simple-mvi-implementation-with-jetpack-compose-5ee5d6fc4908 

    * Mmmh, this already sounds very valuable:

      * > In Compose, the UI is immutable — there’s no way to update it after it’s been drawn, only create a new state and push changes to the compose.  Whenever a state is changed, Compose recreates the parts of the UI tree  that have changed. This process in the Compose is called recomposition.

    * Okay, so apparently, that one uses something called Dagger.Hilt for Dependency Injection

      * Aaand, again, I am running into trouble getting that imported =>,<=

        * I did write it like this, which did also work for the event bus:

          * ````kotlin
            kotlin {
                [...]
                sourceSets {
                    val commonMain by getting
                        dependencies {
                            implementation("org.greenrobot:eventbus:3.3.1")
                            implementation("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
                        }
                    [...]
                }
            }
            ````

        * But that didn't work, and it fails to import `hilt` in this import statement:

          * `import dagger.hilt.android.lifecycle.HiltViewModel`

        * I now figured that for some strange reason, I needed to add the dependency in the `buildscript` part of the top level `build.gradle.kts`, because I don't know why

          * ````kotlin
            buildscript {
                dependencies {
                    [...]
                    classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
                }
            }
            ````

        * Now it gets past `hilt`, but fails at `lifecycle` in the import statement =>,<=

        * I think it also needs some more dependencies in the shared module, because why have all dependencies in the same place =>,<=

        * And then, when I tried to import it like it says here:

          * https://developer.android.com/training/dependency-injection/hilt-android#kts

          * ````kotlin
            plugins {
              kotlin("kapt")
              id("com.google.dagger.hilt.android")
            }
            
            android {
              ...
            }
            
            dependencies {
              implementation("com.google.dagger:hilt-android:2.44")
              kapt("com.google.dagger:hilt-android-compiler:2.44")
            }
            
            // Allow references to generated code
            kapt {
              correctErrorTypes = true
            }
            
            ````

        * ...I get this error:

          * `Type mismatch: inferred type is String but Action<KaptExtension> was expected`

        * I was now able to resolve it by writing it like this, but don't ask me why =>,<=

          * `"kapt"(Dependencies.Hilt.compiler)`

        * Anyway, the error still persists, so maybe I need even more libraries =>,<=

        * I now also added references to:

          * ````kotlin
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
            ````

        * But it's still not working

        * °sob°

        * As a quick action, on the unrecognized `@HiltViewModel` AndroidStudio suggests `Add Library "Gradle: com.google.dagger.hilt-android:2:38:1@aar" to classpath`, but when I try to do that, nothing happens

        * Also, I checked, and that is the dependency that I just imported earlier

        * °sigh°

        * Mmmh, I think the problem is that Hilt really is only for android, and as such it won't work in the shared module

      * That sucks

* This is as far as I'm getting with this today

* So, in the end, I wasn't really able to make any real progress today

* But at least, I did learn the basic truth about why my event system could not update the view, so I guess that's one thing

* Next time, I think I'll look at the `kmpf-materials` sample project again, because I think they did something with models in an advanced tutorial, so mayhap I can copycat something from there



# 30-Dec-2022

* Now continuing with this

* Last time, I tried and failed in getting the backend-frontend communication to work

* This time around, I wanted to take a look at how the `kmpf-materials` sample does it

  * That's the one where the tutorials are behind a paywall starting with chapter 6

  * I looked at the code from Chapter 9 "Dependency Injection", and noticed that one having views not unlike the ones I saw last turn, but I don't know how they got there, and frankly, I have no idea what they do

  * In fact, it *does* look like a completely new tutorial project altogether

  * The previous chapters are:

    * 6. Connect to Platform-Specific API
    7. 7. App Architecture
    8. 8. Testing 

  * I'll now go over the sample code for them one by one to see where the models are introduced and thus hopefully understand them

  * Doesn't look like they're in Chapter 6 "Connect to Platform-Specific API"

  * Looks like the models are added in Chapter 7 "App Architecture", which is also where I'd have expected them

    * Since the paywall begins only some distance into the tutorial, that means I can still read the header of that and hopefully gain some insights from that:

      * https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/7-app-architecture
      * Okay, so unfortunately, it's not very far in before the paywall begins, but it *is* enough to confirm that this is really a wholly new app that was started in Chapter 6, and that it uses the MV(V)C pattern
      * Let's see if this is enough to let me make sense of it

    * One thing I take note of is how the `BaseViewModel` is an abstract expect class in `commonMain`

      * ````kotlin
        expect abstract class BaseViewModel()
        ````

    * In android it is "implemented" like this:

      * ````kotlin
        import androidx.lifecycle.ViewModel
        
        actual abstract class BaseViewModel : ViewModel()
        ````

    * ...while on desktop it is implemented like this:

      * ````kotlin
        actual abstract class BaseViewModel actual constructor()
        ````

    * This has profound implications which probably also relate to my troubles last turn. I should definitely **keep this in mind**

    * Mmmh, I think I am beginning to understand how this is supposed to work together:

      * Every View (which is a `@Composable` function) has a Model attached to it via default parameter

        * ````kotlin
          @Composable
          fun AboutView(viewModel: AboutViewModel = AboutViewModel()) {
            ContentView(items = viewModel.items)
          }
          ````

        * And I think in the DI-Tutorial, this was instead injected from the context

      * The model meanwhile contains the things that can change, at least if I got it right

  * Let's just try it out to see if that's how it works

    * In theory, I suppose that means I rename my `ViewController` into a `ViewModel` and add it to the constructor like this

    * Then, the events should still reach the `ViewModel` as usual, and the screen change will hopefully work again

    * However, now I am running into dependency issues AGAIN =>,<=

      * This time, the `greenrobot` in this import statement is not resolving:

        * ````kotlin
          import org.greenrobot.eventbus.EventBus
          ````

      * ...despite me having imported the eventbus package like this in the `shared` module's `build.gradle.kts`:

        * ````kotlin
          kotlin {
              [...]
              sourceSets {
                  val commonMain by getting
                      dependencies {
                          implementation(Dependencies.eventBus)
                      }
                  [...]
                  }
              }
          }
          ````

      * Meanwhile, in the `shared-ui` module, where it *does* work, I imported it like this:

        * ````kotlin
          kotlin {
              [...]
              sourceSets {
                  val commonMain by getting {
                      dependencies {
                          [...]
                          implementation(Dependencies.eventBus)
                          [...]
                      }
                  }
                  [...]
              }
          }
          ````

      * ...I can't see a difference

      * ...so why isn't it working in the `shared` module? °sob°

      * Okay, so in detailed inspection, I noticed something extremely weird there:

        * In the `shared-ui` module, where it works, the `getting` is syntax-highlighted in yellow, and the `dependencies` is of the type `this: KotlinDependencyHandler`
        * Meanwhile, in the `shared` module, where it doesn't work, `getting` is syntax-highlighted in purple, and `dependencies` is of the type `this: DependencyHandlerScope`
          * HOWEVER, in the next block below that, the `getting` is yellow again, and the `dependencies` is of the (assumedly correct) type again
        * Okay, so text compare helped: Though visually very identical, I was missing a pair of curly braces around the `dependencies` block

      * The import is still not working though =>,<=

      * Or actually, it looks like it is, but for some reason, it is still being displayed as an error even though it works when running the app

      * And with "works", I mean "Clicking on the start button still doesn't take me to the character screen" =<,<=

      * Maybe having a look at the `RemindersView` and its model in the sample project will help me here

        * So, if the app runs, there's a "Reminders" view with a text input box, and upon entering a text and pressing the enter-key, a radio button with the entered text appears, which is drawn differently depending on whether that radio button is checked or not

        * All that happens in the `ContentView` function

        * Okay... things are starting to go from "I don't understand how this is supposed to work" to "I understand how this is supposed to work, and it's downright scary"

          * `RemindersView`

          * ``````kotlin
              var reminders by remember {
                mutableStateOf(listOf<Reminder>(), policy = neverEqualPolicy())
              }
              
              viewModel.onRemindersUpdated = {
                reminders = it
              }
            ``````

          * `RemindersViewModel`

          * ````kotlin
              private val repository = RemindersRepository()
              
              private val reminders: List<Reminder>
                get() = repository.reminders
                
              var onRemindersUpdated: ((List<Reminder>) -> Unit)? = null
                set(value) {
                  field = value
                  onRemindersUpdated?.invoke(reminders)
                }
              
              fun markReminder(id: String, isCompleted: Boolean) {
                repository.markReminder(id = id, isCompleted = isCompleted)
                onRemindersUpdated?.invoke(reminders)
              }
            ````

          * So, if my understanding is correct, the gist of it is that the `RemindersViewModel` has a delegate function `onRemindersUpdated`, which is **null** by default (scary), and is then set by the `RemindersView` in such a way that its `reminders` variable is updated

            * I don't get what the `-> Unit` does though

        * Okay, so setting aside the fact that this does not look nice and I wouldn't want to have it code that I show anyone (primarily because of the `null` assignment, but also because of the duplicate call of `onRemindersUpdated?.invoke(reminders)`), let's see if using this pattern, I can get my code to behave as I want it to 

          * I tried it, but it's still not working
          * Let me debug this to see what happens
          * Mmmh, nope, the state is correctly set now
          * Maybe this is the wrong approach after all, and the real magic happens somewhere else?

        * I now tried writing my `state` variable in the `MainView` like this:

          * ````kotlin
                var state by remember {
                    mutableStateOf(
                        MainViewState.TITLE,
                        policy = neverEqualPolicy()
                    )
                }
            ````

          * BREAKTHROUGH! We won!

          * Alright! So that was the final piece of the puzzle

          * So, after several days of being stuck on this, I now finally managed to get the event-based view changing to work... at least on one occasion

  * Okay, so now that I've got a working version (and committed that), let me see if working from there, I can also get it to work with less ugly

    * Mmmh, looks like this works too, and is less ugly:

      * ````kotlin
        class MainViewModel {
        
            var state = MainViewState.TITLE
        
            var updateState: ((MainViewState) -> Unit) = { }
                set(value) {
                    field = value
                    updateState(state)
                }
                
            @Subscribe
            fun onOpenCharacterSelectionViewEvent(event: OpenCharacterSelectionViewEvent) {
                state = MainViewState.CHARACTER
                updateState(state)
            }
        ````

      * Yes, like this, it looks much more agreeable than that hideous null-and-invoke monstrosity from above

    * I am still not 100% happy with it, but after how much pain this already caused me, I am satisfied enough to leave it like this for now

* Okay, so far, so good, but that is only one screen transition

* Next, let's see if I can also get it to work for the Character Screen using the same paradigm

* However, that is something that won't fit into today anymore

* So this is as far as I'm getting with this today



# 2-Jan-2023

* Now continuing with this

* Last time, I got the basic event-view system to work 

* I now checked that it works equally fine on both the desktop app as well as the android app

* Anyway, next I want to get the event system to work for the character selection

  * I worked on that for a bit, and as I did I came across many, many occurrences where the IDE did not recognize imports, auto-import did not work, and neither did auto-complete

  * Also, the weird block for the `selectedCharacterId` is all in red

  * And yet, the program still compiles and runs without compile errors or exceptions

  * However, now when I click a character button, this message appears in the console:

    * ````
      [FINE] No subscribers registered for event class com.tri_tail.ceal_chronicler.events.SelectCharacterEvent
      [FINE] No subscribers registered for event class org.greenrobot.eventbus.NoSubscriberEvent
      ````

  * That's weird, because I _did_ register a subscriber here:

    * ````kotlin
          @Subscribe
          fun onSelectCharacterEvent(event: SelectCharacterEvent){
              selectedCharacter = Optional.of(event.characterId)
              updateSelectedCharacter(selectedCharacter)
          }
      ````

    * Ah, wait, I think the `CharakterSelectorModel` is still missing this:

      * ````kotlin
            init {
                val eventBus = EventBus.getDefault()
                eventBus.register(this)
            }
        ````

    * Okay, so now this works

  * However, the desired behavior does not occur

  * Instead, when I click a character button now, the character buttons start switching places perpetually

    * This is possibly related to this red block that I haven't been able to figure out:

      * ````kotlin
            var selectedCharacterId: Optional<CharacterId> by remember {
                mutableStateOf(
                    model.selectedCharacter,
                    policy = neverEqualPolicy()
                )
            }
        ````

    * The errors here are:

      * ````
        Property delegate must have a 'getValue(Nothing?, KProperty<*>)' method. None of the following functions is suitable:
        public inline operator fun <T> State<Optional<CharacterId>>.getValue(thisObj: Any?, property: KProperty<*>): Optional<CharacterId> defined in androidx.compose.runtime
        
        Property delegate must have a 'setValue(Nothing?, KProperty<*&>, Optional<CharacterId>)' method. None of the following functions is suitable:
        public inline operator fun <T> MutableState<Optional<CharacterId>>.setValue(thisObj: Any?, property: KProperty<*>, value: Optional<CharacterId>): Unit defined in androidx.compose.runtime
        ````

    * I now tried it like this:

      * ````kotlin
            var selectedCharacterId: MutableState<Optional<CharacterId>> =
                remember { mutableStateOf(model.selectedCharacter) }
        ````

      * With that, selecting a character now works once, and when I click on "Back", I am returned to the character selection, but then I am stuck there, and the buttons occasionally switch places if I click them

      * One weird thing that I observe is that if I set a breakpoint in `onSelectCharacterEvent`, than that one triggers repeatedly if I click on a character button

    * I now tried it like this:

      * ````kotlin
            var selectedCharacterId: MutableState<Optional<CharacterId>> =
                remember {
                    mutableStateOf(
                        model.selectedCharacter,
                        policy = neverEqualPolicy()
                    )
                }
        ````

      * That causes the perpetual back-and-forth-switching again

      * I debugged it, and I observed the following:

        * When first entering the character screen, the method `DisplaySelectableCharacters` is called once, as intended
        * When clicking on a character button, the  method `DisplayCharacterScreen` is called, and the correct character screen is displayed, as intended
        * However, then the method `DisplaySelectableCharacters` is immediately called in the next frame for no obvious reason, and the character screen disappears again
        * After that, `DisplaySelectableCharacters` is repeatedly called, and the `selectedCharacterId` has a a different Object ID every time, which probably has something to do with the `policy = neverEqualPolicy()`

      * Further debugging showed that there may be an ID issue, so I now implemented the `RandomReadableId` class to make this easier to debug

        * When the character selection screen is first opened, the `CharakterRepository` contains characters with these IDs:
          * Tame Jay Sigma
          * Bold Zebra Kappa
        * And when I then click on the first button, the character with the ID "Tame Jay Sigma" is opened
        * After returning back to the character selection, the `CharakterRepository` contains characters with these IDs:
          * Alert Quail Gamma
          * Ugly Rat Psi
        * That is already one level of worrying, but also understandable at some level, because I already feared that the app might create a new `CharacterRepository` at each step because it's currently not remembered
        * When I then click on the first button, the `selectedCharacterId` correctly becomes "Alert Quail Gamma"
          * However, the code still jumps into the block that should happen if the character could not be found in the repository
          * Interestingly, I observe that by that time, the repository contains these character IDs:
            * Sad Koala Tau
            * Great Ibex Iota
          * Also, I note that the event was apparently fired multiple times for some reason

      * Anyway, while I do not understand the whole depth of the problem, I figure that the most damaging part currently is that the `CharacterRepository` is not being remembered

      * Yes, looks like that fixed it 

      * However, it looks like the events are still being thrown multiple times, and exactly one additional time for each time I click the buttons (First once, then twice, then thrice...)

        * I note that while the event is only thrown once each time I click on the button, it is then handled one additional time for each time it has been thrown before
        * Aaah, I think I get why...
        * It's because the `CharacteSelectorModel` is also not remembered, so I get a new one for each time 
        * Next I should definitely do the Dependency Injection, because that would also have fixed this

  * With that, the Event System works

* This is as far as I'm getting today with this



# 4-Jan-2023

* Now continuing with this
* Today, I wanted to get started with Dependency Injection
  * Chapter 9 of the `kmpf_materials` sample project deals with that
  * And here's the respective scrambled article:
    * https://www.kodeco.com/books/kotlin-multiplatform-by-tutorials/v1.0/chapters/9-dependency-injection
    * Unfortunately, the scrambling begins too early for that article to be really useful, so let's see if I can figure it out based on the sample project
  * So, apparently, it uses Koin, which is this:
    * https://insert-koin.io/
    * That led me to these interesting pages:
      * https://insert-koin.io/docs/setup/annotations
      * https://github.com/InsertKoinIO/hello-kmp/tree/annotations
      * https://kotlinlang.org/docs/ksp-multiplatform.html#avoid-the-ksp-configuration-on-ksp-1-0-1
    * I checked out the `hello-kmp` project
      * Once again, this claims to be KMP, but is only KMM
      * Looks like I'll have to slingshot my way through this using the `kmpf_materials` sample project
      * At any rate, it will probably do for me to read out through this Koin tutorial first:
        * https://insert-koin.io/docs/reference/introduction
    * Okay, looks like I managed to get it running
      * I now managed to get the `MainViewModel` from the context
      * Now let's see if I can get the others from there too
      * Yes, looks good!
  * Looks like I managed to add dependency injection to all my _three_ different types of backend classes
  * The only thing that's a bit annoying is that I have to pass the `koin` reference through everywhere, which is needed because the frontend doesn't use classes, so I can't have them inherit from `KoinComponent`
    * I figure I could put it into a global variable, like how the `kmpfMaterials` sample project did it (at least I _think_ that's what's happening there...), but I have instead chosen to pass it through, because that makes it easy to understand where it comes from





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

## Notable things that don't work

* Images
* Previews



# Benchmarks

## Language

* Overall: Neutral (0)
* Kotlin
* (+) Data classes
* (+) Enforces when-statements (switch/case) to be exhaustive
* (-) Events are not as straightforward as they should be
* (-) Kotlin Multiplatform does not really implement object-oriented programming, which causes problems (for example with events)

## IDE

* Overall: Very bad (---)
* (+) The IDE is Android Studio, which is free
* (+) Refactoring, code navigation and syntax highlighting basically work
* (-) Refactoring sometimes does not correctly adjust code in other modules
* (-) Auto import is unreliable, sometimes offers only garbage, and overall I end up having to manually write/copy imports a lot
* (-) Syntax highlighting takes several seconds to update on a change, and so does auto-complete
* (-) Regularly displays errors where there are none
  * That is, the IDE claims that references can't be resolved and half the lines are red, but at runtime everything works fine
  * And then, auto-complete and auto-import naturally no longer works
* (-) Sometimes does not offer certain options like "New > Package" in some projects, forcing you to add folders manually if it auto-collapsed a chain of folders - like "models.main_view" - and you can't add a folder below "models" in the IDE anymore
* (-) Asks every day if you want to add new files to git, even if you check "Don't ask again" 


## Project setup

* Overall: Extremely bad (----)
* (-) A Kotlin Multiplatform Mobile project is set up easily via wizard, but a true multiplatform project that also supports desktop needs to be done manually
* (-) At the end, you end up with a Moloch with six modules and just as many `build.gradle.kts` files, which are already full of dependencies
  * Put simply: You *start* already with dependency hell
  * (--) And dependencies **regularly** fail to resolve for no apparent reason
    * At this point, one of my biggest time sinks is trying to figure out why dependencies won't resolve
    * Dependencies also end up all over the place in different build files, and it is practically impossible to keep an overview over how it is all connected
    * And they're quite big, to the point where it feels like most of my project is actually build files =>,<=
    * Sometimes dependencies fail to resolve in one project although they work just fine in another project, implemented in just the same way

## Multiplatform support

* Overall: Kinda bad (-)
* It fells like it's half-baked yet
* (-) Android and Desktop can share the same frontend code, but for iOS you need to program in Swift
  * (-) Sharing resources like XMLs and images, while possible, is complicated and requires third-party extensions
* (+) Backend code can be shared across all projects

## Dependency Injection

* Overall: Kinda good (+)
* Uses Koin: https://insert-koin.io
* (+) Works fine and without any trouble

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

## How Tos

### Cross-Platform Images

* You need the dependency `moko-resources` for that (https://github.com/icerockdev/moko-resources)

  * Note that it might not work with the latest version of the android plugin
    * In my case, I needed to set the version of the android plugin to `7.2.2`

* Apart from the android plugin version, all the changes need to happen in the `shared-ui` module

* In the `shared-ui/build.gradle.kts` add:

  * ````kotlin
    kotlin {
        [...]
        sourceSets {
            val commonMain by getting {
                dependencies {
                    [...]
                    api("dev.icerock.moko:resources:0.20.1")
                    api("dev.icerock.moko:resources-compose:0.20.1")
                }
            }
            [...]
        }
    }
    ````

* Add the following files:

  *  `shared-ui/src/commonMain/kotlin/com/domain/project/ResourcesExt.kt`

  * ````kotlin
    package com.domain.project
    
    import androidx.compose.ui.graphics.painter.Painter
    import dev.icerock.moko.resources.ImageResource
    
    expect fun painterResource(imageResource: ImageResource): Painter
    ````

  * `shared-ui/src/androidMain/kotlin/com/domain/project/ResourcesExt.kt`

  * ````kotlin
    package com.domain.project
    
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.graphics.painter.Painter
    import dev.icerock.moko.resources.ImageResource
    
    @Composable
    actual fun painterResource(imageResource: ImageResource): Painter {
        return androidx.compose.ui.res.painterResource(id = imageResource.drawableResId)
    }
    ````

  * `shared-ui/src/desktopMain/kotlin/com/domain/project/ResourcesExt.kt`

  * ````kotlin
    package com.domain.project
    
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.graphics.painter.Painter
    import androidx.compose.ui.graphics.toPainter
    import dev.icerock.moko.resources.ImageResource
    
    @Composable
    actual fun painterResource(imageResource: ImageResource): Painter {
        return imageResource.image.toPainter()
    }
    ````

* The image needs to go under `shared-ui/src/commonMain/resources/MR/images/MyImage@1x.png`

  * I'm not sure what the `@1x` is there for, but it is definitely required, because otherwise there'll be an error when starting the app

* In the composable method where you want to add the image, add:

  * ...these dependencies:

    * ````kotlin
      import androidx.compose.foundation.Image
      import com.domain.project.MR
      import com.domain.project.painterResource
      ````

  * ...this where you want to display the image:

    * ````kotlin
              Image(
                  painter = painterResource(MR.images.MyImage),
                  contentDescription = "Some Description"
              )
      ````

### Event-based screen-changing

* Import the Greenrobot Eventbus dependency in the `build.gradle.kts` of the `shared` and `shared-ui` modules

  * ````kotlin
    kotlin {
        [...]
        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation("org.greenrobot:eventbus:3.3.1")
                    [...]
                }
            }
            [...]
        }
    }
    ````

* Define the event in `shared.commonMain` like this:

  * ````
    class OpenCharacterSelectionViewEvent {
    }
    ````

* Fire the event like this:

  * ````kotlin
    import com.tri_tail.ceal_chronicler.events.OpenCharacterSelectionViewEvent
    import org.greenrobot.eventbus.EventBus
    
    private fun clickButton() {
        val eventBus = EventBus.getDefault()
        eventBus.post(OpenCharacterSelectionViewEvent())
    }
    ````

* Create a view model like this that handles the event and provides a delegate function handle that the view can use to get the changed value:

  * ````kotlin
    import com.tri_tail.ceal_chronicler.events.OpenCharacterSelectionViewEvent
    import org.greenrobot.eventbus.EventBus
    import org.greenrobot.eventbus.Subscribe
    
    class MainViewModel {
    
        var state = MainViewState.TITLE
    
        var updateState: ((MainViewState) -> Unit) = { }
            set(value) {
                field = value
                updateState(state)
            }
    
        init {
            val eventBus = EventBus.getDefault()
            eventBus.register(this)
        }
    
        @Subscribe
        fun onOpenCharacterSelectionViewEvent(event: OpenCharacterSelectionViewEvent) {
            state = MainViewState.CHARACTER
            updateState(state)
        }
    }
    ````

* In the view, make the `state` a `remember` with a `mutableStateOf(..., policy = neverEqualPolicy())`, and the assign the delegate  like this:

  * ````kotlin
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.*
    import com.tri_tail.ceal_chronicler.models.main_view.MainViewModel
    import com.tri_tail.ceal_chronicler.models.main_view.MainViewState
    import com.tri_tail.ceal_chronicler.theme.AppTheme
    import com.tri_tail.ceal_chronicler.ui.TitleScreen
    import com.tri_tail.ceal_chronicler.ui.characters.DisplayCharacterSelector
    
    @Composable
    fun MainView(model: MainViewModel = MainViewModel()) {
    
        var state by remember {
            mutableStateOf(
                model.state,
                policy = neverEqualPolicy()
            )
        }
    
        model.updateState = {
            state = it
        }
    
        AppTheme {
            when (state) {
                MainViewState.TITLE -> TitleScreen()
                MainViewState.CHARACTER -> DisplayCharacterSelector()
            }
        }
    }
    ````



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

