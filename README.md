Here is a professional and detailed **README.md** file for your project.

You can create a file named `README.md` in the root folder of your project, copy the content below, and paste it in. I have added placeholders (like `[Your Name]`) that you should fill in before uploading.

---

```markdown
# Expense Tracker & Reminder App 💰📅

A comprehensive Android application designed to help users manage their personal finances, track daily income and expenses, analyze spending habits via charts, and manage upcoming bill reminders.

This project uses a **Client-Server Architecture** (Option B) with a native Android Client (Kotlin) and a custom Backend API (PHP/MySQL).

![Project Banner](screenshots/banner.png) 
*(Note: Upload screenshots to a 'screenshots' folder in your repo and link them here)*

## 📱 Features

*   **User Authentication:** Secure Login and Registration system using MySQL.
*   **Dashboard:** Real-time overview of Total Income, Total Expense, and Current Balance.
*   **Transaction Management:** Add and view daily income/expense records with color-coded visual cues.
*   **Analytics:** Visual breakdown of finances using a Pie Chart (integrated with MPAndroidChart).
*   **Bill Reminders:** Manage upcoming bills, mark them as 'Paid'/'Unpaid', and delete obsolete records.
*   **Settings:**
    *   **Currency Conversion:** Toggle globally between USD ($) and VND (₫).
    *   **Logout:** Securely clear user session.
*   **Online Storage:** All data is synced to a MySQL database via RESTful APIs.

## 🛠 Tech Stack

### Android Client
*   **Language:** Kotlin
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Networking:** Retrofit 2 & OkHttp (with timeout & retry logic)
*   **Concurrency:** Kotlin Coroutines
*   **UI Components:** ViewBinding, Navigation Component, RecyclerView, CardView.
*   **Chart Library:** MPAndroidChart

### Backend
*   **Server:** Apache (via WampServer/XAMPP)
*   **Language:** PHP 8.0+
*   **Database:** MySQL
*   **Format:** JSON

## 🚀 Installation & Setup Guide

Since this project uses a local server, follow these steps strictly to run the app.

### Prerequisites
1.  **Android Studio** (Koala/Ladybug or newer).
2.  **WampServer** (recommended) or XAMPP installed on your PC.
3.  **Git** installed.

### Step 1: Backend Setup (PHP & MySQL)

1.  **Locate the API Code:**
    *   Navigate to the `backend/` folder in this repository (or copy the PHP files provided in the source code).
    *   Copy the folder `expense_api` into your server's root directory:
        *   **WampServer:** `C:\wamp64\www\expense_api\`
        *   **XAMPP:** `C:\xampp\htdocs\expense_api\`

2.  **Database Configuration:**
    *   Start your WampServer/XAMPP (Ensure Apache and MySQL are green).
    *   Open **phpMyAdmin** in your browser (`http://localhost/phpmyadmin`).
    *   Create a new database named: `expense_tracker_db`.
    *   Click on the SQL tab and run the script found in `database/schema.sql` (or copy the SQL below):

<details>
<summary>Click to view SQL Script</summary>

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL
);

CREATE TABLE expenses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(10) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE bills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'UNPAID',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```
</details>

3.  **Check Connection:**
    *   Open your browser and visit: `http://localhost/expense_api/db_connect.php`
    *   If the page is **Blank/White**, the connection is successful.
    *   If you see an error, check your `db_connect.php` file (username/password).

### Step 2: Android Setup

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/[your-username]/expense-tracker-app.git
    ```
2.  **Open in Android Studio:**
    *   Open Android Studio -> File -> Open -> Select the project folder.
    *   Let Gradle sync completely.

3.  **Configure IP Address (Crucial):**
    *   The app is configured to work with the **Android Emulator**.
    *   In `data/api/RetrofitClient.kt`, ensure the Base URL is:
        ```kotlin
        private const val BASE_URL = "http://10.0.2.2/expense_api/"
        ```
    *   *Note: `10.0.2.2` is the special IP that allows the Emulator to access your computer's localhost.*

4.  **Run the App:**
    *   Select an Emulator (e.g., Pixel 4 API 33).
    *   Click the **Run** (Play) button.

## 📂 Project Structure

```
com.example.expensetracker
├── data                # Data Layer
│   ├── api             # Retrofit Service & Client
│   └── model           # Data Classes (User, Expense, Bill)
├── ui                  # UI Layer (Fragments & ViewModels)
│   ├── login           # Authentication
│   ├── home            # Dashboard & Expense List
│   ├── stats           # Charts & Analytics
│   ├── bills           # Bill Reminders
│   ├── settings        # Currency & Logout
│   └── MainActivity.kt # Container Activity
├── utils               # Utilities (SessionManager)
└── worker              # Background Workers
```

## 📸 Screenshots

| Login | Dashboard | Analytics | Bills |
|:---:|:---:|:---:|:---:|
| ![Login](screenshots/login_thumb.png) | ![Home](screenshots/home_thumb.png) | ![Stats](screenshots/stats_thumb.png) | ![Bills](screenshots/bills_thumb.png) |

*(Remember to replace these paths with actual image files)*

## 👥 Contributors

*   **[Student Name 1]** - Backend Logic & Database
*   **[Student Name 2]** - UI/UX Design & Navigation
*   **[Student Name 3]** - Advanced Features (Analytics, Bills)

## 📄 License

This project is created for educational purposes as part of the Mobile Device Application Development course.

---
```

### **How to prepare your project for GitHub:**

1.  **Clean the project:** inside Android Studio, go to **Build > Clean Project**. This reduces the file size.
2.  **Create a folder named `backend` (or `php_api`)** inside your project folder and copy all your PHP files there. This ensures the backend code is saved on GitHub too.
3.  **Create a folder named `database`** and save your SQL code into a file named `schema.sql`.
4.  **Take Screenshots:** Run your app, take screenshots of the 4 main screens, put them in a folder named `screenshots`, and link them in the README.
5.  **Upload:** Use Git to push everything to GitHub.
