-- CREATE TABLES
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

-- CREATE USER
INSERT INTO users (id, email, password, full_name) VALUES 
(1, 'demo@gmail.com', '123456', 'Demo User');

-- INCOMES
INSERT INTO expenses (user_id, title, amount, date, type) VALUES 
(1, 'Monthly Salary', 2000.00, '2024-05-01', 'INCOME'),
(1, 'Freelance Project', 450.00, '2024-05-15', 'INCOME'),
(1, 'Gift from Mom', 100.00, '2024-05-20', 'INCOME');

-- EXPENSES
INSERT INTO expenses (user_id, title, amount, date, type) VALUES 
(1, 'Groceries', 85.50, '2024-05-02', 'EXPENSE'),
(1, 'Starbucks Coffee', 5.25, '2024-05-03', 'EXPENSE'),
(1, 'Pizza Night', 24.00, '2024-05-05', 'EXPENSE'),
(1, 'Lunch with Coworkers', 15.00, '2024-05-08', 'EXPENSE'),
(1, 'Uber Ride', 12.50, '2024-05-04', 'EXPENSE'),
(1, 'Bus Monthly Pass', 40.00, '2024-05-01', 'EXPENSE'),
(1, 'Mobile Data Plan', 20.00, '2024-05-10', 'EXPENSE'),
(1, 'New Sneakers', 120.00, '2024-05-12', 'EXPENSE'),
(1, 'Cinema Tickets', 18.00, '2024-05-14', 'EXPENSE'),
(1, 'Netflix Subscription', 14.99, '2024-05-01', 'EXPENSE');

-- Bills
INSERT INTO bills (user_id, title, amount, due_date, status) VALUES 
(1, 'Electricity Bill', 65.00, '2024-05-05', 'PAID'),
(1, 'Water Bill', 25.50, '2024-05-10', 'PAID'),
(1, 'Home Internet', 45.00, '2024-05-28', 'UNPAID'),
(1, 'House Rent', 800.00, '2024-06-01', 'UNPAID'),
(1, 'Credit Card Payment', 150.00, '2024-05-30', 'UNPAID');