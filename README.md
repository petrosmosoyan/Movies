# Movies App - Android Technical Assignment

A modern, production-ready Android application that displays a list of top-rated movies and their details, featuring offline support, pagination, and a favorites system.

## 🚀 Features
- **Movie List**: Paginated list of top-rated movies.
- **Movie Details**: Comprehensive view with metadata (Budget, Revenue, Overview).
- **Offline Support**: Fully functional offline mode using Room as a local cache.
- **Pagination**: Infinite scrolling powered by Paging 3 and `RemoteMediator`.
- **Favorites**: Persisted "Favorite" system that survives cache refreshes.
- **Modern UI**: Edge-to-edge support, custom layouts, and smooth state transitions.

## 🏗 Architecture
The app follows **Clean Architecture** principles combined with **MVVM**, split into three distinct layers:

### 1. Data Layer
- **Retrofit**: Handles API communication with TMDB.
- **Room**: Acts as the **Single Source of Truth (SSOT)**. All data shown in the UI comes from the local database.
- **RemoteMediator**: Orchestrates synchronization between the network and local database.
- **Mappers**: Converts API DTOs to Database Entities and Domain Models.

### 2. Domain Layer
- **Use Cases**: Contains single-purpose business logic (e.g., `MoviesUseCase`, `ToggleFavoriteUseCase`). This ensures logic is reusable and decoupled from the ViewModels.
- **Models**: Plain Kotlin data classes representing the core business entities.

### 3. Presentation Layer
- **MVVM**: ViewModels manage UI state using `StateFlow`.
- **Paging 3**: Handles the complex logic of list updates and scroll position management.
- **ViewBinding**: Type-safe access to XML layouts.
- **Navigation Component**: Manages fragment transactions and Safe Args passing.

## 🛠 Technical Stack
- **Kotlin** & **Coroutines/Flow**
- **Dagger Hilt**: Dependency Injection.
- **Jetpack Paging 3**: For efficient list loading.
- **Glide**: Image loading and caching.
- **Material Components**: Modern UI design.

## 📖 Key Decisions & Trade-offs

### Single Source of Truth (SSOT)
I decided to display data exclusively from the Room database. When the user scrolls, the `RemoteMediator` fetches new data and saves it to Room, which then updates the UI.
- **Decision**: Even the Details screen observes a Flow from the database.
- **Trade-off**: Requires more boilerplate (Mappers, Daos), but results in a significantly better UX where data is consistent across the whole app even when offline.

### UI Load State Handling
I chose to drive the UI states (Loading, Error, Retry) directly from the `PagingDataAdapter.loadStateFlow`.
- **Decision**: This avoids manual boolean flags in the ViewModel and ensures the UI perfectly matches the internal state of the Paging library.

## ⚙️ Setup Instructions
To protect sensitive data, the API key is not included in the source code.

1. Get an API Read Access Token from [TMDB](https://www.themoviedb.org/settings/api).
2. Open `local.properties` in the root folder.
3. Add the following line:
   ```properties
   API_KEY=YOUR_TOKEN_HERE
   ```
4. Sync Gradle and Run the app.

## 📈 Future Improvements
- **Unit Testing**: Adding tests for UseCases using JUnit and Turbine.
- **Search**: Implementation of a search feature using the Paging 3 source.
