# Expense Tracker & Reminder App

A comprehensive Android application designed to help users manage their personal finances, track daily income and expenses, analyze spending habits via charts, and manage upcoming bill reminders.

This project uses a **Client-Server Architecture** with a native Android Client (Kotlin) and a custom Backend API (PHP/MySQL).

![Project Banner](screenshots/banner.png) 
*(Note: Upload screenshots to a 'screenshots' folder in your repo and link them here)*

## Features

*   **User Authentication:** Secure Login and Registration system using MySQL.
*   **Dashboard:** Real-time overview of Total Income, Total Expense, and Current Balance.
*   **Transaction Management:** Add and view daily income/expense records with color-coded visual cues.
*   **Analytics:** Visual breakdown of finances using a Pie Chart (integrated with MPAndroidChart).
*   **Bill Reminders:** Manage upcoming bills, mark them as 'Paid'/'Unpaid', and delete obsolete records.
*   **Settings:**
    *   **Currency Conversion:** Toggle globally between USD ($) and VND (₫).
    *   **Logout:** Securely clear user session.
*   **Online Storage:** All data is synced to a MySQL database via RESTful APIs.

## Tech Stack

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

## Installation & Setup Guide

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
