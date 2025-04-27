# Lab 3 Part 1

1) How was the camera preview implemented?
   1) The camera preview was implemented using the `CameraScreen` composable function in `CameraScreen.kt`. It uses `PreviewView` from the CameraX library to display the camera feed. The `bindCameraUseCases` function sets up the camera preview, image analysis, and binds them to the lifecycle of the activity.

2) On the bottom sheet, the user can change the settings, e.g., choose a different EfficientNet model while using the app, and the app will start using the new setting. How was this feature implemented?  
   1) The bottom sheet is implemented using `BottomSheetScaffold` in `MainActivity.kt`. The settings are updated dynamically by interacting with UI components like `ModelSelection`, `OptionMenu`, and `AdjustItem`. These components trigger methods in `MainViewModel` (e.g., `setModel`, `setDelegate`, `setThreshold`) to update the `Setting` state, which is observed by the app to apply the new configuration.

3) How did this app specify which deep learning models to be included in the apk, and where are these two deep learning models stored?  
   1) The app specifies the deep learning models in the `ImageClassificationHelper.Options` class. The models are stored in the `assets` directory of the app. The `ImageClassificationHelper` class loads the models using TensorFlow Lite's `Interpreter`.

4) What is the data and control flow to run inference on each camera frame and display the result on the bottom sheet?
   1) The `CameraScreen` composable captures frames using `ImageAnalysis`. Each frame is passed to the `classify` method in `MainViewModel`, which launches a coroutine to process the frame using `ImageClassificationHelper.classify`. The classification results are emitted as a `StateFlow` and combined with other settings in `MainViewModel` to update the `UiState`. The `BottomSheet` composable observes `UiState` and displays the results dynamically.
