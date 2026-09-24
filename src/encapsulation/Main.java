package encapsulation; // Match your package name

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SupermarketManager manager = new SupermarketManager();

        // Load saved inventory from file on startup
        manager.getInventory().addAll(FileHandler.loadInventory());

        boolean running = true;
        System.out.println("Welcome to the Supermarket System!");

        while (running) {
            System.out.println("1. Add New Product");
            System.out.println("2. View All Products");
            System.out.println("3. Search Product by ID");
            System.out.println("4. Search Product by Name");
            System.out.println("5. Update Product");
            System.out.println("6. Delete Product");
            System.out.println("7. Restock Product");
            System.out.println("8. Filter by Category");
            System.out.println("9. Sort Products by Price");
            System.out.println("10. Process Sale (Checkout)");
            System.out.println("11. Low Stock Report");
            System.out.println("12. Inventory Value Report");
            System.out.println("13. Top Selling Products");
            System.out.println("14. View Sales History");
            System.out.println("15. Save & Exit");
            System.out.print("Enter your choice: ");

            int choice = getValidInt(scanner);

            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    String id = scanner.nextLine();   // <-- SIMPLE STRING INPUT

                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Price: ");
                    double price = getValidDouble(scanner);

                    System.out.print("Enter Quantity: ");
                    int qty = getValidInt(scanner);

                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();

                    Product newProduct = new Product(id, name, price, qty, category);
                    manager.addProduct(newProduct);
                    break;

                case 2:
                    manager.viewAllProducts();
                    break;

                case 3:
                    System.out.print("Enter Product ID to search: ");
                    String searchId = scanner.nextLine();
                    Product found = manager.findProductById(searchId);
                    if (found != null) {
                        System.out.println("Found: " + found.toString());
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Product ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new Price: ");
                    double newPrice = getValidDouble(scanner);
                    System.out.print("Enter new Quantity: ");
                    int newQty = getValidInt(scanner);
                    manager.updateProduct(updateId, newPrice, newQty);
                    break;

                case 5:
                    System.out.print("Enter Product ID to delete: ");
                    String deleteId = scanner.nextLine();
                    manager.deleteProduct(deleteId);
                    break;

                case 6:
                    manager.processSale(scanner);
                    break;

                case 7:
                    manager.lowStockReport();
                    break;

                case 8:
                    manager.inventoryValueReport();
                    break;

                case 9:
                    FileHandler.viewSalesHistory();
                    break;

                case 10:
                    FileHandler.saveInventory(manager.getInventory());
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    // ============ HELPER: Safe Integer Input ============
    public static int getValidInt(Scanner scanner) {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine();
            }
        }
    }

    // ============ HELPER: Safe Double Input ============
    public static double getValidDouble(Scanner scanner) {
        while (true) {
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a decimal number: ");
                scanner.nextLine();
            }
        }
    }
}