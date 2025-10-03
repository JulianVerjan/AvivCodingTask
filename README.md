#### Project setup

1. The latest version of Android Gradle Plugin is needed to run the project.
2. Gradle version is needed to run the project. 
3. Clone the project. 
4. Compile and run the app on a device or emulator.

## Design of the app
- The app was made using jetpack compose to made the UI implementation more faster.
- Airbnb's lottie library was used to show some error images and give the app a nice look
- Single activity approach was follow for a better app performance and code simplicity.

## Architecture

The architecture of the application is based on the following points:

- A single-activity architecture, using the [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to manage navigation operations.
- Pattern [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (MVVM) facilitating a [separation](https://en.wikipedia.org/wiki/Separation_of_concerns) and organization between the view layer and network layer.
- [S.O.L.I.D](https://en.wikipedia.org/wiki/SOLID) design principles intended to make software designs more understandable, flexible and maintainable.
- [Modular app architecture](https://proandroiddev.com/build-a-modular-android-app-architecture-25342d99de82) allows to be developed features in isolation, independently from other features.

### Modules

The next graph shows the app modularisation:

- `:app` indirectly depends on `:features`.
- `:features` modules depends on `:lib:domain` module.
- `:lib:network` contains only android native frameworks for backend communication and testing. This layer don’t depends on any UI framework. This project implements `:lib:domain` contracts to transform the information from network object to data objects.
- `:lib:data` depends of `:lib:domain` and `:lib:network`. Contains Dto objects and repositories implementations to make the app works(Repository implementation that fetch properties information). The repositories implementations converts the data from network object to Dto objects and propagates the information to use cases implementations, then the data is use in the view models. This module also contains the API interface used to retrieve data from the REST API.
- `:lib:domain` Contains use cases interfaces and implementations. Repositories interfaces that are implemented inside the `:lib:data` in order to fetch, organize and pass the data to the view models. This module doesn't have any dependency.
- `:lib` doesn't have any dependency.

#### App module

The`:app` module is the main entry of the client app.  It is also responsible for initiating the [dependency graph]

#### Core module(Network)

The `:lib:network` module is an android library  for serving network requests. Providing all the necessary object to make network request. and mapping the data for the `:lib:data` module.

#### Core module(Data)

The `:lib:data` module is an android library and contains the repository implementation that fetch the data from the `:lib:network`. This layer use `:lib:domain` module and its interfaces to handle the information requests and propagate the info between the data layer and the view models.  

#### Core module(Domain)

The `:lib:domain` module is an android library that contains all use cases interfaces/implementations and repositories. This module acts as an intermediary in the communication of information.  Having the `:lib:domain` module and its interfaces the app testability is increased also.

#### Features modules

In the `:features` module you will find the logic, Compose views, view models and UI resources to make the app works.

#### Libraries modules

The `:lib` modules basically contains different utilities that can be used by the different modules.

#### Technology

Some of the main technologies used for this app are:

1. Dagger Hilt for handling the dependency injection
2. Jetpack compose for UI implementation.
3. Kotlin language.
4. Navigation for handling the navigation inside the app.
5. Coroutines for fetching the data and async communication.
6. Mockito, Junit and Mockk libraries were use to create the unit tests
7. Okhttp vs retrofit for information requests from the client app.

#### What was achieved during the test

1. Coroutines were used for the communication with the API.
2. The app was written entirely in kotlin.
3. In the application the information is being handled as if they were states that update the UI.
4. SOLID and clean architecture principles were applied.
5. An architecture by modules was used thinking about the scalability of the project and to allow other developers to understand and scale the project more easily.
6. A ViewModel pattern was used to communicate the changes from the data and network layers to the UI layer.
7. Unit tests were implemented for all the layers in the app.

#### Thanks for the opportunity given, it was a lot of fun for me to take this test.

