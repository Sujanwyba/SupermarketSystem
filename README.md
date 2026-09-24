# Supermarket Billing and Stock System

A Java console application that manages a supermarket's products, processes sales, and generates reports. All data is stored persistently using CSV files.

## Features
- **CRUD**: Add, view, update, and delete products
- **Search**: Find products by ID or by partial name
- **Organization**: Filter by category and sort by price
- **Billing**: Process sales with automatic 10% discount on totals over $100
- **Stock Control**: Restock products and get low-stock alerts
- **File Handling**: Inventory saved to `inventory.csv`, receipts saved to `sales_history.txt`
- **Reports**: Inventory value, low stock, top-selling products, and sales history

## How to Run
1. Clone the repository or download the source files
2. Open the project in IntelliJ IDEA
3. Run `Main.java`
4. Use the numbered menu to navigate

## Technologies
- Java (JDK 17+)
- Object-Oriented Programming (Encapsulation, Constructors)
- File I/O: `BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`
- Collections: `ArrayList`
- Input: `Scanner`

## Project Structure
- `Main.java` — Menu-driven console interface
- `Product.java` — Product model with encapsulation
- `SupermarketManager.java` — Business logic for inventory and billing
- `FileHandler.java` — CSV load/save utility

## Author
Sujan
