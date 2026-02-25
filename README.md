# 📚 Library Management System

> A console-based Library Management System built with **Core Java**, **JDBC**, and **MySQL** — following a clean, professional **DAO Pattern** with Interface-Implementation separation and a dedicated **Service Layer** for business logic.

---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![JDBC](https://img.shields.io/badge/JDBC-Pure-green?style=flat-square)
![IDE](https://img.shields.io/badge/IDE-Eclipse-purple?style=flat-square&logo=eclipseide)

---

## 🚀 Features

### 📖 Book Management
- Add new books with title, author, category, published year, and copy count
- View all available books in a formatted table
- Update book details
- Delete books from the system
- Track total copies vs. available copies separately

### 👤 Member Management
- Register new library members with contact details
- Set a maximum book borrowing limit per member
- View, update, and remove members
- Membership date tracking

### 🔄 Transaction Management
- Issue books to members with issue date and **due date (14 days)**
- Return books and update availability automatically
- **Borrowing limit validation** — prevents issuing beyond member's limit
- **Overdue fine calculation** — ₹2 per day after due date
- Track transaction status (`ISSUED` / `RETURNED`)
- View full transaction history

### 🛡️ Input Validation
- Rejects negative numbers and zero for IDs, copies, and limits
- Rejects empty strings for required fields
- Custom `InvalidInputException` for clear, specific error messages

---

## 🗂️ Project Structure

```
LibraryManagementSystem/
│
├── src/
│   ├── module-info.java
│   └── com/library/
│       │
│       ├── Main.java                        # Entry point
│       │
│       ├── controller/                      # User interaction & menus
│       │   └── LibraryController.java
│       │
│       ├── service/                         # Business logic layer
│       │   └── LibraryService.java
│       │
│       ├── dao/                             # DAO Interfaces
│       │   ├── BookDao.java
│       │   ├── MemberDao.java
│       │   └── TransactionDao.java
│       │
│       ├── dao/impl/                        # DAO Implementations
│       │   ├── BookDaoImpl.java
│       │   ├── MemberDaoImpl.java
│       │   └── TransactionDaoImpl.java
│       │
│       ├── model/                           # Plain Java Objects
│       │   ├── Book.java
│       │   ├── Member.java
│       │   └── Transaction.java
│       │
│       ├── exception/                       # Custom Exceptions
│       │   └── InvalidInputException.java
│       │
│       └── util/
│           ├── DbUtil.java                  # JDBC Connection Utility
│           └── InputValidator.java          # Input validation utility
│
├── README.md
└── .gitignore
```

---

## 🧠 Architecture Overview

```
Main
  ↓
LibraryController        ← handles menus & user input
  ↓
LibraryService           ← business logic (validation, fine calc, limit check)
  ↓
DAO Interface Layer
 (BookDao, MemberDao, TransactionDao)
  ↓
DAO Implementation Layer
 (BookDaoImpl, MemberDaoImpl, TransactionDaoImpl)
  ↓
MySQL Database

  Model     → Plain Java objects (Book, Member, Transaction)
  Service   → Business rules and validations
  DAO       → All SQL & JDBC logic
  Util      → DB connection and input validation
  Exception → Custom exceptions for clean error handling
```

---

## 🗄️ Database Schema

### 📘 `books`
```sql
CREATE TABLE books (
    book_id          INT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(200) NOT NULL,
    author           VARCHAR(100) NOT NULL,
    category         VARCHAR(100),
    published_year   INT,
    total_copies     INT NOT NULL,
    available_copies INT NOT NULL
);
```

### 👤 `members`
```sql
CREATE TABLE members (
    member_id         INT AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    email             VARCHAR(100) UNIQUE NOT NULL,
    phone             VARCHAR(15),
    membership_date   DATE NOT NULL,
    max_books_allowed INT NOT NULL
);
```

### 🔄 `transactions`
```sql
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id        INT NOT NULL,
    member_id      INT NOT NULL,
    issue_date     DATE NOT NULL,
    due_date       DATE NOT NULL,
    return_date    DATE,
    status         ENUM('ISSUED', 'RETURNED') NOT NULL,
    FOREIGN KEY (book_id)   REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);
```

---

## ⚙️ Setup & How to Run

**1. Clone the repository**
```bash
git clone https://github.com/roshani1104/library-management-system.git
```

**2. Create the database**
```sql
CREATE DATABASE library_db;
USE library_db;
-- Then run the table creation scripts above
```

**3. Add DB credentials**

Create a file `src/db.properties` (this file is gitignored):
```properties
db.url=jdbc:mysql://localhost:3306/library_db
db.user=your_mysql_username
db.password=your_mysql_password
```

**4. Add MySQL Connector/J**

Right-click project → **Build Path → Add External JARs** → select `mysql-connector-j-x.x.x.jar`

**5. Run the project**

Run `Main.java` from Eclipse.

---

## 🎯 What This Project Demonstrates

- ✅ **DAO Design Pattern** — clean interface + implementation separation
- ✅ **Service Layer** — business logic separated from controller and DAO
- ✅ **JDBC Best Practices** — `PreparedStatement`, `try-with-resources`
- ✅ **Java Modules** — use of `module-info.java` for modular project structure
- ✅ **`LocalDate` & SQL Date mapping** — modern Java date types integrated with JDBC
- ✅ **Null safety** — nullable `return_date` and `due_date` handled gracefully
- ✅ **Custom Exceptions** — `InvalidInputException` for meaningful error messages
- ✅ **Input Validation** — dedicated `InputValidator` utility class
- ✅ **Fine Calculation** — overdue fine of ₹2/day calculated on return
- ✅ **Borrowing Limit Validation** — enforced before issuing a book
- ✅ **Secure Credentials** — DB credentials loaded from `db.properties`, not hardcoded
- ✅ **Clean Code** — meaningful naming, separation of concerns, helper mapping methods

---

## 📌 Future Improvements

- [ ] More custom exceptions (BookNotFoundException, MemberNotFoundException, etc.)
- [ ] Spring Boot REST API
- [ ] Web UI with HTML / CSS / JavaScript
- [ ] Hibernate / JPA for ORM
- [ ] Unit testing with JUnit 5
- [ ] Dockerized MySQL setup

---

## 👩‍💻 Author

**Roshani Dangat**
Java Developer | Backend Enthusiast

[![GitHub](https://img.shields.io/badge/GitHub-roshani1104-black?style=flat-square&logo=github)](https://github.com/roshani1104)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Roshani%20Dangat-blue?style=flat-square&logo=linkedin)](https://www.linkedin.com/in/roshani-dangat/)

---

> ⭐ If you found this project helpful, give it a star and feel free to fork!
