Fullstack E-Commerce Application

A full-stack e-commerce web application built using **Spring Boot (Java 21)** and **React 19**. It provides secure authentication, product management, shopping cart, and order handling features.


✨ Features

* 🔐 User Registration & Login (JWT Authentication)
* 🛍️ Product Management (Add / View / Update / Delete)
* 🛒 Shopping Cart System
* 📦 Order Placement & Order History
* 👤 User Role-based Access (User/Admin structure)
* 🔒 Secure REST APIs with Spring Security
* ⚛️ Responsive React Frontend

---

🛠️ Tech Stack

Backend:

* Spring Boot
* Java 21
* Spring Security
* JPA / Hibernate
* MySQL
* JWT Authentication

Frontend:

* React 19
* JavaScript
* HTML / CSS

Tools:

* Maven
* Git & GitHub

---

 📂 Project Structure

```
ecommerce/
│
├── backend (Spring Boot)
│   └── src/main/java/com/nitin/ecommerce
│
├── frontend (React)
│   └── src/
│
└── pom.xml
```

---

⚙️ How to Run Project

### 🔧 Backend

```bash id="b1"
mvn spring-boot:run
```

---

 🎨 Frontend

```bash id="b2"
cd frontend
npm install
npm start
```

---

## 🔗 API Endpoints

* `/api/auth/register` → Register user
* `/api/auth/login` → Login user
* `/api/products` → Get products
* `/api/cart` → Cart operations
* `/api/orders` → Order management

---

🎯 Purpose

This project is built for learning full-stack development using Spring Boot and React, and demonstrates real-world e-commerce functionality.

---

👨‍💻 Author

* GitHub: [nitinkushwah627-lang](https://github.com/nitinkushwah627-lang)

---

⭐ Future Improvements

* Payment gateway integration
* Admin dashboard
* Product search & filters
* Deployment on cloud (Render/Vercel)
* Email notifications

---
