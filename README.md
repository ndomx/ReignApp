# ReignApp
A simple post feed made in Android Studio

## How to build
> This app was made with `Android Studio Chipmunk | 2021.2.1 Patch 2`

The SDK location is the only thing you need to provide in order to compile this project. You
can do this by defining a valid SDK location with an ANDROID_HOME environment variable, or by
following these steps:
1. After loading the project, go to the _Project Structure_ window, located in `File->Project Structure`.
Alternatively, you can use the command `Ctrl+Alt+Shift+S` in Windows
2. In the side menu, locate `SDK Location`
3. Inside this menu, there should be 2 fields, one for the SDK location and the other for the
NDK location. This project does not make use of the Android NDK, so you will only need to locate
the Android SDK in the topmost field
4. After locating the Android SDK, click _Ok_ and build the project. The IDE should then create a `local.properties` file
containing the SDK path, and you should be ready to go
