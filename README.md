# VASS Budget Tracker App

A simple Android app for tracking personal expenses, managing categories, setting spending goals, and filtering expenses by time period or category.

---

## Features
- Add, edit, and delete expenses.
- Categorize your spending.
- Set minimum and maximum monthly spending goals.
- View total spending by category.
- Filter expenses by date range or category.
- Capture and attach photos (optional).
- Login screen for user authentication (hardcoded credentials).

---

## Technologies Used
- Kotlin
- Android Studio
- Room Database
- MVVM Architecture
- LiveData and ViewModel
- Material Design Components
- GitHub Actions for CI/CD (Gradle build and test)

---

## Project Structure
vcmsa.projects.budgettrackerapp │ ├── data (entities, dao, database builder) ├── repository (data access logic) ├── viewmodel (business logic for UI) ├── ui (activities, adapters, layouts) ├── utils (helpers and constants if any) └── .github/workflows (CI/CD automation scripts)


## ⚙️ How to Build and Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
2. Open the project in Android Studio.

3. Let Gradle sync and download dependencies.

4. Build and run on an Android device or emulator.

5. Default login credentials:

   - Username: shivar

   - Password: password

GitHub Actions CI
- Automatically builds and tests the app when pushing to the master branch.

- Gradle tasks executed:

- assembleDebug

- test

Demo Video
https://youtu.be/BwjJLUys-04

Author
Developers: Shivar Tuplah, Ahmed Vally, Shaymen Gerard Kista, Vidur Somaru

Organization: VASS

License
This project is licensed under the MIT License - see the LICENSE file for details.

Notes
Minimum SDK Version: 32

Target SDK Version: 35

No backend or online authentication (local login only).

Images are stored locally using URIs.
