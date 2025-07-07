<h1>📦 Ecommerce Order Service</h1>
<p>This is a Spring Boot application that simulates an e-commerce order placement system. It covers full order flow including stock validation, payment simulation, order creation, and failure handling — following clean architecture principles and robust error management.</p>

<h2>🛠️ Technologies Used</h2>

| Technology           | Version         |
| -------------------- | --------------- |
| Java                 | 17              |
| Spring Boot          | 3.2             |
| Spring Web           | -               |
| Spring Data JPA      | -               |
| H2 Database          | In-Memory       |
| Lombok               | -               |
| Validation (Jakarta) | -               |
| Gradle               | -               |
| Spock (Groovy)       | 2.4 (for tests) |

<h2>📚 Project Structure</h2>

<h2>📦 Features & Flow</h2>
<h3>✅ Place Order Flow</h3>
<ol>
<li>Check if product is in stock.</li>
<li>Deduct stock using optimistic locking (retry if needed).</li>
<li>Simulate payment using success rate logic.</li>
<li>Create and save order with PENDING, SUCCESS, or FAILED status.</li>
<li>If payment fails, restore stock.</li>
</ol>
<h3>✅ Cancel Order Flow</h3>
<ol>
<li>Check if order exists and is cancellable.</li>
<li>Restore stock.</li>
<li>Mark order as CANCELLED.</li>
</ol>

<h2>🧱 Component Responsibilities</h2>
<h3>📦 Controller Layer</h3>

| Controller          | Responsibility                                  |
| ------------------- | ----------------------------------------------- |
| `OrderController`   | Handles placing, canceling, and listing orders. |
| `UserController`    | Exposes endpoints to fetch user details.        |
| `ProductController` | Fetches product list or individual product.     |

<h3>⚙️ Service Layer</h3>

| Service            | Responsibility                                                                    |
| ------------------ | --------------------------------------------------------------------------------- |
| `OrderService`     | Core order logic: validate stock, simulate payment, persist order, handle cancel. |
| `PaymentService`   | Simulates payment success/failure with artificial delay and input validation.     |
| `InventoryService` | Validates and adjusts product stock with optimistic locking.                      |
| `UserService`      | Fetches user data.                                                                |
| `ProductService`   | Fetches product data.                                                             |

<h3>🗃 Repository Layer</h3>
<p>Standard Spring Data JPA Repositories:</p>
<ul>
<li>OrderRepository</li>
<li>UserRepository</li>
<li>ProductRepository</li>
</ul>

<h2>📌 REST API Endpoints with Examples</h2>
<h3>✅ 1. Place Order</h3>
<p><b><u>POST /api/orders</u></b></p>
<p>Place a new order by providing user ID, product ID, quantity, and payment details.</p>
<p><b>Request:</b></p>

```json
{
  "userId": 1,
  "productId": 2,
  "quantity": 3,
  "paymentInfoDto": {
    "type": "CREDIT_CARD",
    "cardNumber": "4111111111111111",
    "expiry": "12/26",
    "cvv": "123"
  }
}
```
<p><b>Successful Response:</b></p>

```json
{
  "orderId": 1001,
  "status": "SUCCESS",
  "message": "Order placed successfully!"
}
```

<p><b>Failed Payment Response:</b></p>

```json
{
  "orderId": 1002,
  "status": "FAILED",
  "message": "Order failed due to payment error!"
}
```

<p><b>Error Response (Out of Stock):</b></p>

```json
{
  "timestamp": "2025-07-07T12:30:15.123",
  "message": "Insufficient stock!",
  "status": 409,
  "path": "/api/orders"
}
```

<h3>✅ 2. Cancel Order</h3>
<p><b><u>PUT /api/orders/{orderId}/cancel</u></b></p>
<p>Cancels an existing order and restores the product stock.</p>
<p><b>Request:</b></p>
<p><u>(No request body needed, only path variable orderId)</u></p>
<p><b>Example URL:</b></p>

```bash
PUT /api/orders/1001/cancel
```

<p><b>Successful Response:</b></p>

```json
{
  "orderId": 1001,
  "status": "CANCELLED",
  "message": "Order cancelled and stock restored!"
}
```

<p><b>Error Response (Already Cancelled):</b></p>

```json
{
  "orderId": 1001,
  "status": "CANCELLED",
  "message": "Order cancelled and stock restored!"
}
```

<p><b>Error Response (Not Found):</b></p>

```json
{
  "timestamp": "2025-07-07T12:45:30.789",
  "message": "Order not found!",
  "status": 404,
  "path": "/api/orders/9999/cancel"
}
```

<h2>✅ How to Run</h2>

```bash
# Clone and go to directory
git clone https://github.com/Anjaniy2000/Anjaniy2000-ecommerce-order-service.git
cd Anjaniy2000-ecommerce-order-service

# Run with Gradle
./gradlew bootRun
```

