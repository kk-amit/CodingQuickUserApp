# Assignment 

We would like you to complete a small Android project. Below is a list of the requirements for this project. Below is a list of the requirements for this project. 
Please use the following requirements to create a basic Android application:

1.  A minimum of 2 screens (a detail screen and a list screen)
2.  List screen should be populated by the sample api end point( https://fake-json-api.mock.beeceptor.com/users). For any reason if this doesn't work use any public api endpoints. Ex: https://github.com/public-apis/public-apis
3. Build this is a modularized architecture (even though this is a small project), using compose, kotlin, DI (either Dagger, Koin or Hilt), coroutines
4. Include unit tests
5. Include a ReadMe file with technologies used, any assumptions and/or improvements, please remember that this does not need to be a complete project!

Note: The content on the screens is not important and please do not spend a lot of time on this app (between 6 - 8 hours max)


### Solution 

Based on the given assignment, I have analyzed the requirements and outlined the following User Stories and Acceptance Criteria. In a real development environment, each user story should be tracked via a dedicated JIRA ticket, and work should begin in a corresponding feature branch linked to that ticket for better traceability. I also aim to follow clean code repository guidelines. Each user story will be implemented in a separate branch, and every pull request (PR) will include a single commit to keep the history clean and support easier cherry-picking when required. If needed, commits can be squashed during merge for better maintainability.

**Below are the list of stories as team we planned.**

**Story 1: Setting Up the Project with Clean Architecture**

As a developer building a robust app,
I want to organize my project using clean architecture and modularization,
So that it’s easier to test, scale, and maintain over time.

**Acceptance Criteria:**
- Code is separated into ui, presentation, domain, and data modules.
- Presentation follows MVVM pattern.
- Layers only depend on the layer directly below them.

---

**Story 2: Dependency Injection with Hilt**

As a developer,
I want to use Hilt for dependency injection,
So that I don’t need to manage object creation manually.

**Acceptance Criteria:**
- Hilt is initialized in the Application class.
- Dependencies (ViewModels, Repositories, UseCases, etc.) are injected via constructors.
- Proper DI modules are created for networking, DB, and app-level components.

---

**Story 3: Fetching Users from API and  Display Users in a List**

As a user,
I want to see a list of users fetched from a remote API,
So that I can browse through them conveniently.

I want to see all users in a scrollable list,
So that I can pick one to view more details.

I want the app to try again automatically when a request fails,
So that I don’t have to retry manually.

**Acceptance Criteria:**

- Retrofit is used to call https://fake-json-api.mock.beeceptor.com/users.
- The result is parsed and shown using Compose.
- A fallback or alternate API is used if the main one fails.
- Compose LazyColumn is used to display the list.
- Items include avatar, name, and email.
- Clicking an item navigates to a detail screen.
- Retrofit calls are wrapped in a Flow.
- retryWhen operator is used for retry logic with delay.
- Error/loading states are handled with user feedback.

---

**Story 4: Working Offline with Room**

As a user,
I want to still view users even without internet,
So that I don’t lose access to important info.

**Acceptance Criteria:**

- User data is saved to Room DB upon API success.
- If offline, the list shows the last cached data.
- The sync process is seamless in the background.

---

**Story 5: User Detail Screen**

As a user,
I want to see detailed information for each user,
So that I can understand more than just their name and email.
Implement the Error Screen 

**Acceptance Criteria:**

- Shows full details: name, phone, address, email, company, etc.
- UI is clean and readable.
- Back navigation works properly.

---


**Story 6: Unit Testing**

As a developer,
I want to test the app's logic thoroughly,
So that I can be confident my code works correctly and is maintainable.

**Acceptance Criteria:**

- ViewModels and UseCases are unit tested.
- Repositories are mocked/faked for isolated tests.
- Error and success scenarios are covered.

---

**Story 7: Helpful README**

As a developer or reviewer,
I want a clear README that explains the project setup and structure,
So that I can quickly understand the goals and tech choices.

**Acceptance Criteria:**
- README includes tech stack, architecture diagram, and setup steps.
- Mentions any design decisions or known limitations.
- Lists future improvement ideas if applicable.