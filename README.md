# VASS Budget Tracker App

A clean, simple and powerful Android app for tracking personal expenses, managing categories, setting goals, visualizing spending, and more. Built for ease of use and effective budget control.

---

## Features

- User Authentication via login screen (local hardcoded credentials)
- Add, edit, and delete expenses
- Categorize your expenses with custom categories
- Set monthly spending goals and track them
- Filter expenses by date, category, or both
- View total spending by category
- Visualize expenses using a bar chart
- Dark Mode toggle for better user experience
- "Remember Me" option for login persistence
- Help Page with contact and usage instructions
- Reset view and delete all expenses
- Firebase Firestore integration (basic test write)
- [Optional] Camera/photo features (not implemented)

---

## Technologies Used

- Language: Kotlin
- IDE: Android Studio
- Architecture: MVVM (Model-View-ViewModel)
- Database: Room (SQLite)
- UI Toolkit: Material Design Components
- LiveData & ViewModel: For reactive UIs
- Firebase Firestore: For cloud data storage (test-only)
- GitHub Actions: For Continuous Integration (build + unit tests)

---

## Project Structure

```
vcmsa.projects.budgettrackerapp
├── data/         # Entities, DAOs, and Database builder
├── repository/   # ExpenseRepository
├── viewmodel/    # ExpenseViewModel
├── ui/           # Activities, Adapters, Layouts
├── utils/        # Utility classes (if applicable)
└── .github/workflows/  # CI/CD GitHub Actions workflow
```

---

## How to Build and Run

1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   ```

2. Open the project in Android Studio

3. Allow Gradle to sync and download dependencies

4. Run the app on a physical device or emulator

5. Login Credentials:
   - Username: shivar
   - Password: password

---

## Demo Video

[Watch the Demo](https://youtu.be/W9JGUvCsDuk)

---

## CI/CD via GitHub Actions

- Auto-triggered on push to `master` branch
- Performs:
  - assembleDebug
  - test

---

## Developers

- Shivar Tuplah
- Ahmed Vally
- Shaymen Gerard Kista
- Vidur Somaru

Organization: VASS

---

## License

This project is licensed under the MIT License — see the `LICENSE` file for more details.

---

## Notes

- Minimum SDK Version: 32  
- Target SDK Version: 35  
- No backend or online authentication – all data is stored locally.
- All images (if implemented) will be stored locally using URIs.
