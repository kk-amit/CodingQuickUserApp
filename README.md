# Assignment User App
UserApp is a Android app that fetches and displays user details from a public API. It includes a list screen and a detail screen. Built with Jetpack Compose, Retrofit, Hilt (DI), Coroutines, ROOM, and unit tests, the app follows a modular architecture for scalability and maintainability.

1. **Below are the main Android components used to develop this Android app**

- **Jetpack Compose** for UI Development
- **Retrofit** is used to make network API calls
- **Hilt** is used for Dependency Injection 
- **Coroutines** are used to perform asynchronous tasks in a lightweight, non-blocking way
- **Room** is used as a local database library, This is used in this app to save data locally
- **Unit tests** are used to verify that individual components works as expected

---

2. **Requirement Analysis and User story identification**
   
   I have created a page for Requirements Understanding and User stories. Please visit
   [Requirement Analysis](https://github.com/kk-amit/CodingQuickUserApp/blob/main/Design/UserStory.md) 

---
3. **User APP Design**

   After requirement analysis, I went through some best practices and chose to develop this app in Clean Architecture + MVVM and I planned to use the Android Google recommended concept for Data management i.e. Single Source of
   data.
   
   - **Clean Architecture** I analyzed the project and planned the four layers for this project i.e.
      - ui layer
      - presentation layer
      - domain layer
      - data layer

      For more details, Please visit Uncle Bob original doc https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

   - **MVVM** I plan to manage the presentation layer using MVVM, making the code cleaner, more maintainable, scalable, and easier to unit test.
  
   - **Single Source of Truth** The Single Source of Truth (SSOT) principle ensures that a particular piece of data is managed and updated from only one source, typically the repository layer. This helps maintain data consistency
     across the app and avoids conflicts or duplication. In the User App we developed, we followed the SSOT approach to manage data efficiently. Our repository handles both local and remote data sources. Initially, we display data
     from the local database to ensure quick UI rendering, while fresh data is fetched in the background. Once the remote data is retrieved, it is stored in the local database, and the user list view is updated using StateFlow.

     For more details, Please visit Android Developer https://developer.android.com/topic/architecture/data-layer#source-of-truth

		<img src="/Design/SSOT.png" width="800">



---
4. **Screens**

   In the current app, Right now we have three screens to manage the Users. 
   

   - User List Screen 
   - User Details Screen 
   - Error Screen

   Please refer to below screen-shots 
   
   <img src="/Screenshots/UserList_Screen.png" width="300">  <img src="/Screenshots/UserDetail_Screen.png" width="300">  <img src="/Screenshots/Error_Screen.png" width="300">

---
5. **Improvements**

   In the current app, we can plan below improvements in future

   - **Pagination** : Right now we are loading the list data in single API call, So for future improvements we can make the API based on Pagination and load the data based on pages.
   - **Security** : Right now we are not using any authentication, for production we must use some authentication for enhance securities.
   - **Lint Analysis** : Due to time permit I haven’t perform the Lint analysis, In future we can plan the Lint analysis and bug fixes.
   - **Add user profile detail screen with edit/update support.**
  
6. **App Structure and how to use**

   Please clone this repository using below command
   
   Use `git clone https://github.com/kk-amit/CodingQuickUserApp.git` to clone the repository.

   Once you clone the code, below are the structure defined for this repository

		
		CodingQuickUserApp/
		├── README.md
		├── AppSourceCode
		├── Design
		├── Screenshots
		└── LICENSE


   Please open the folder AppSourceCode in Android Studio to open the app.

   Below are the version details used to create this APP

   - **Android Studio :** Android Studio Koala | 2024.1.1 Patch 1
   - **Gradle Version :** 8.7
   - **Android Gradle Plugin Version :** 8.5.1