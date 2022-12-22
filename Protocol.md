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
  * 