# ✈️ **FlightSearch**
**FlightSearch** is a modern Android application that allows users to search for flights departing from any airport using IATA codes or airport names. It offers real-time auto-suggestions based on a local Room database, and allows users to save their favorite routes. The app follows modern Android development practices with **Room**, **ViewModel**, **StateFlow**, and **Preferences DataStore**, structured around the **MVVM** architecture.


## ✅ **LAST MAJOR UPDATES**

   - First commit with Theme, Font and colors.
   - Logo App implemented.
   - README implemented.
      
## ❌ **NEXT UPDATES**

- 🧱 Design and implement the local SQLite database with **Room**:
   - Define `AirportEntity` and `RouteEntity` models.
   - Populate a prebuilt Room database with airports and routes.
   - Set up the `FlightDao` with SQL queries for airport search and destination lookups.

- 🧠 Build the repository layer:
  - Create `FlightRepository` to encapsulate data access.
  - Expose results as `Flow` for reactive UI updates.

- 🏗 Set up dependency injection for the repository and database:
  - Implement a simple `AppContainer` or use **Hilt** (to define).
  - Inject the repository into the `ViewModel`.

- 📦 Create the `ViewModel` and initial `UiState`:
  - Connect search input to the database via repository.
  - Reflect database results in a composable `StateFlow`.

- 🔍 Implement the search screen UI:
  - Add `TextField` with auto-complete support.
  - Display real-time filtered results from Room.

- ❤️ Add favorites logic (DataStore + Room):
  - Mark routes as favorites and persist selection.
  - Display favorite routes when no search is active.

## 📋 **Features**

   - 🔎 **Search Flights** :

      - ❌ **NOT IMPLEMENTED** Suggest airports using auto-complete as user types.
      - ❌ **NOT IMPLEMENTED** Show destination list from selected airport.
      - ❌ **NOT IMPLEMENTED** Display airport name + IATA code from DB.
   
   - ❤️ **Favorites Management** :

      - ❌ **NOT IMPLEMENTED** Save and delete favorite flight routes.
      - ❌ **NOT IMPLEMENTED** Show favorites when no search is typed.

   - 💾 **Preferences**:

      - ❌ **NOT IMPLEMENTED** Save latest search input via Preferences DataStore.
      - ❌ **NOT IMPLEMENTED** Restore search text and display related results at app launch.

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

      - ❌ **NOT IMPLEMENTED** Use of StateFlow for UI state handling.
      - ❌ **NOT IMPLEMENTED** ViewModel for lifecycle-aware logic.
      - ❌ **NOT IMPLEMENTED** Coroutines for async data operations.

   - 📦 Data Persistence:

      - ❌ **NOT IMPLEMENTED** Persist inventory data locally using Room (SQLite).
      - ❌ **NOT IMPLEMENTED** Automatically restore inventory after app restart.

   - 🧠 Architecture & Code Structure:

      - ❌ **NOT IMPLEMENTED** MVVM architecture pattern.
      - ❌ **NOT IMPLEMENTED** Clean separation between UI and business logic.

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