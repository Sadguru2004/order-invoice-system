# Order Invoice System

A backend application built with **Java and Spring Boot** that automates the complete order-to-invoice process.

The system allows a customer order to be created through a REST API, stores the order in MySQL, generates a professional PDF invoice, and sends the invoice to the customer's email as an attachment.

## 🚀 Features

* Create customer orders using REST API
* Request validation using DTOs
* Store orders in MySQL
* JPA/Hibernate for database operations
* Automatic invoice PDF generation
* Professional invoice format
* HTML email template
* PDF invoice attachment in email
* Global exception handling
* Application logging using SLF4J
* Clean layered architecture

## 🔄 Application Workflow

```text
Client / Postman
       │
       ▼
REST API
       │
       ▼
Order Controller
       │
       ▼
Order Service
       │
       ├──► Save Order ──────► MySQL
       │
       ├──► Generate Invoice ─► PDF
       │
       └──► Send Email ──────► Customer
                                │
                                └── PDF Attachment
```

## 🛠️ Technologies Used

| Technology         | Purpose                |
| ------------------ | ---------------------- |
| Java               | Backend development    |
| Spring Boot        | Application framework  |
| Spring Web         | REST APIs              |
| Spring Data JPA    | Database operations    |
| Hibernate          | ORM                    |
| MySQL              | Database               |
| Jakarta Validation | Request validation     |
| OpenPDF            | Invoice PDF generation |
| Spring Mail        | Email sending          |
| SLF4J / Logback    | Application logging    |
| Maven              | Dependency management  |
| Postman            | API testing            |
| IntelliJ IDEA      | Development            |

## 📂 Project Structure

```text
src
└── main
    ├── java
    │   └── com.sadguru.orderinvoice
    │       ├── controller
    │       ├── dto
    │       ├── entity
    │       ├── exception
    │       ├── repository
    │       └── service
    │
    └── resources
        └── application.properties
```

## 📡 API

### Create Order

**POST**

```text
/api/orders
```

### Request Body

```json
{
  "customerName": "Sadguru Patil",
  "customerEmail": "customer@example.com",
  "productName": "Laptop",
  "quantity": 2,
  "price": 50000
}
```

### Response

```json
{
  "success": true,
  "message": "Order created successfully",
  "orderId": 1
}
```

## 🧮 Invoice Calculation

The invoice calculates the total amount using:

```text
Total Amount = Price × Quantity
```

For example:

```text
Price     = ₹50,000
Quantity  = 2

Total     = ₹1,00,000
```

## 📄 Invoice Generation

After the order is successfully saved:

1. Order information is retrieved.
2. A professional invoice PDF is generated.
3. Invoice number/order ID is included.
4. Customer and billing information is added.
5. Product details and total amount are displayed.
6. The PDF is attached to the confirmation email.

## 📧 Email Notification

The system sends an HTML-formatted confirmation email containing:

* Customer name
* Product name
* Quantity
* Price
* Total amount
* Order ID
* PDF invoice attachment

## 📝 Logging

The application uses **SLF4J logging** to track important application events.

Example:

```text
Creating order for customer: customer@example.com
Order saved successfully with ID: 10
Generating invoice PDF for order ID: 10
Invoice PDF generated successfully for order ID: 10
Preparing invoice email for: customer@example.com
Sending invoice email for order ID: 10
Invoice email sent successfully to: customer@example.com
```

This makes it easier to monitor the application and troubleshoot errors.

## 🗄️ Database

The application uses **MySQL** to persist order information.

Example order data:

| Field          | Example                                             |
| -------------- | --------------------------------------------------- |
| ID             | 10                                                  |
| Customer Name  | Sadguru Patil                                       |
| Customer Email | [customer@example.com](mailto:customer@example.com) |
| Product Name   | Laptop                                              |
| Quantity       | 2                                                   |
| Price          | ₹50,000                                             |

## ⚙️ Configuration

Configure your MySQL database and email credentials in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/order_invoice_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_APP_PASSWORD
```

> Never upload real passwords, Gmail App Passwords, API keys, or other secrets to GitHub.

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

### 2. Open the project

Open the project in IntelliJ IDEA.

### 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE order_invoice_db;
```

Update the database credentials in `application.properties`.

### 4. Configure Gmail

Use a Gmail App Password instead of your normal Gmail password.

### 5. Start the application

Run the Spring Boot application.

### 6. Test the API

Use Postman:

```text
POST http://localhost:8080/api/orders
```

Send the JSON request shown above.

## 📸 Project Screenshots

### Postman — Create Order

*Add Postman screenshot here.*

### MySQL — Saved Order

*Add database screenshot here.*

### Generated Invoice PDF

*Add invoice PDF screenshot here.*

### Email With Invoice Attachment

*Add received email screenshot here.*

## 🎯 What I Learned

Through this project, I practiced:

* Building REST APIs with Spring Boot
* DTO-based request handling
* Bean validation
* JPA/Hibernate
* MySQL database integration
* Service-layer architecture
* PDF generation
* HTML email creation
* Email attachments
* Exception handling
* Logging
* API testing with Postman
* Building an end-to-end backend workflow

## 👨‍💻 Author

**Sadguru Patil**

Java Developer | Spring Boot | Backend Development

---

⭐ If you find this project useful, consider giving the repository a star.
