<h1>🛒 E-commerce Order Service</h1>
<br>
<p>This project implements a simple e-commerce order placement functionality using Spring Boot. It validates product availability, simulates payments, manages stock, and records order status in an in-memory H2 database.</p>

<h3>📌 Overview</h2>
When a user places an order:
<ol>
<li>The system checks whether the product is in stock.</li>
<li>If available, it deducts the stock (with concurrency-safe logic).</li>
<li>It stores the order with a status (SUCCESS, FAILED, PENDING, CANCELLED).</li>
<li>In case of payment failure, stock is restored and order status is updated to FAILED.</li>
<li>Users can also cancel existing orders.</li>
</ol>

<h3>⚙️ Technologies & Frameworks Used</h3>
<table>

</table>
