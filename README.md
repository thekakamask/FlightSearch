# ✈️ **FlightSearch**
**FlightSearch** is a modern Android application that allows users to search for flights departing from any airport using IATA codes or airport names. It offers real-time auto-suggestions based on a local Room database, and allows users to save their favorite routes. The app follows modern Android development practices with **Room**, **ViewModel**, **StateFlow**, and **Preferences DataStore**, structured around the **MVVM** architecture.


## ✅ **LAST MAJOR UPDATES**

- 🧱 Designed and implemented the **local SQLite database using Room**:
  - Defined `Airport` and `Favorite` entities matching the prebuilt database scheme.
  - Integrated a pre-populated Room database (`flight_search.db`) using `.createFromAsset(...)`.
  - Created `AirportDao` and `FavoriteDao` with SQL queries for search, selection, and favorite route tracking.

- 🧠 Built the **Repository** layer:
  - Defined `AirportRepository` and `FavoriteRepository` interfaces.
  - Implemented `OfflineAirportRepository` and `OfflineFavoriteRepository` classes using Room DAOs.
  - Exposed database results as `Flow<T>` for reactive and lifecycle-aware UI updates.

- 🧩 Implemented **Dependency** injection setup:
  - Created `AppContainer` interface for managing shared dependencies.
  - Built `AppDataContainer` to provide repository instances from the Room database.
  - Integrated everything into `FlightSearchApplication` for global access to dependencies.

- 💾 Added **User Preferences DataStore** support:
  - Defined `IUserPreferencesRepository` and implemented `UserPreferencesRepository` for persistent search query storage.
  - Used `Preferences DataStore` to persist the latest search query (`search_query`) with `stringPreferencesKey`.
  - Injected via `AppContainer` for access from the `ViewModel`.

- 🎛️ Structured the **UI State management**:
   - Defined `FlightUiState` to represent the complete state of the search screen, including search query, results, favorites, and loading/error state.
   - Created `FlightUiModel` to represent enriched flight route items (departure, destination, favorite status).

- 📦 Create the `ViewModel` and `UiState`:
   - Inject repositories into the ViewModel.
   - Implement search + favorite + preferences logic using repositories.
   - Reflect database results in a composable `StateFlow`.
      
## ❌ **NEXT UPDATES**

- 🔍 Implement the search screen UI:
  - Add `TextField` with auto-complete support.
  - Display real-time filtered results from Room.

- ❤️ Add favorites logic (DataStore + Room):
  - Mark routes as favorites and persist selection.
  - Display favorite routes when no search is active.

## 📋 **Features**

   - 🔎 **Search Flights** :

      - 🟩 **IN PROGRESS** Suggest airports using auto-complete as user types.
      - 🟩 **IN PROGRESS** Show destination list from selected airport.
      - 🟩 **IN PROGRESS** Display airport name + IATA code from DB.
   
   - ❤️ **Favorites Management** :

      - 🟩 **IN PROGRESS** Save and delete favorite flight routes.
      - 🟩 **IN PROGRESS** Show favorites when no search is typed.

   - 💾 **Preferences**:

      - 🟩 **IN PROGRESS** Save latest search input via Preferences DataStore.
      - 🟩 **IN PROGRESS** Restore search text and display related results at app launch.

   - 🎨 Modern and Fluid Interface:

      - ❌ **NOT IMPLEMENTED** Follows Material Design 3 guidelines.
      - ❌ **NOT IMPLEMENTED** Smooth transitions with Navigation Component.
      - ❌ **NOT IMPLEMENTED** Responsive layout with adaptive UI.

      - TopBar:
         - ❌ **NOT IMPLEMENTED** Display application title and possible future actions.

      - Light/Dark Mode:
         - ✅ **DONE** Supports **light/dark mode**.

      - Custom theme:
         - ✅ **DONE** Implemented custom colors and shapes.
         - ✅ **DONE** Implemented **Google font** "Urbanist" and "Inter".

   - 🔄 Real-time status management:

      - ✅ **DONE** Use of StateFlow for UI state handling.
      - ✅ **DONE** ViewModel for lifecycle-aware logic.
      - ✅ **DONE** Coroutines for async data operations.

   - 📦 Data Persistence:

      - ✅ **DONE** Persist airports and favorites data locally using Room (SQLite).
      - 🟩 **IN PROGRESS** Automatically restore datas after app restart.

   - 🧠 Architecture & Code Structure:

      - 🟩 **IN PROGRESS** MVVM architecture pattern.
      - 🟩 **IN PROGRESS** Clean separation between UI and business logic.

   - 🚀 Performance and responsiveness:
   
      - ❌ **NOT IMPLEMENTED** Optimize UI scrolling and animations.
      
   - 🛠 Error Handling & User Feedback:

      - ❌ **NOT IMPLEMENTED** : To define.

## 🛠️ **Tech Stack**

   - **Kotlin**: Modern, concise language for Android development.
   - **Jetpack Compose**: Declarative UI toolkit for Android.
   - **Material 3**: Modern, accessible user interface.
   - **Kotlin Flow**: Reactive streams for data.
   - **StateFlow**: Reactive state management for real-time updates.
   - **ViewModel**: MVVM architecture to separate business logic from user interface.
   - **Room**: Local database with DAO and entities;
   - **State Management**: Handle states with MutableStateOf and StateFlow.
   - **Navigation Component**: Seamless screen transitions.
   - **Coroutines**: Async programming made simple.
   - **DataStore**: Modern replacement for SharedPreferences.
   
## 🚀 **How to Use**
   
   ❌ **THIS SECTION IS NOT IMPLEMENTED YET**


## 📸 **Screenshots**

   ❌ **THIS SECTION IS NOT IMPLEMENTED YET**



## 🤝 **Contributions**
Contributions are welcome! Feel free to fork the repository and submit a pull request for new features or bug fixes✅🟩❌.