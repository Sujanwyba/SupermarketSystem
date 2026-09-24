package encapsulation;

import java.util.ArrayList;
import java.util.Scanner;

public class SupermarketManager {

    // This ArrayList holds all products in our "warehouse"
    private ArrayList<Product> inventory = new ArrayList<>();

    // ============ ADD PRODUCT ============
    public void addProduct(Product p) {
        // Check for duplicate ID
        for (Product existing : inventory) {
            if (existing.getId().equals(p.getId())) {
                System.out.println("Error: Product with ID " + p.getId() + " already exists!");
                return;
            }
        }
        inventory.add(p);
        System.out.println("Success: Product '" + p.getName() + "' added to inventory.");
    }

    // ============ VIEW ALL PRODUCTS ============
    public void viewAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n===== FULL INVENTORY =====");
        for (Product p : inventory) {
            System.out.println(p.toString());
        }
        System.out.println("===========================\n");
    }

    // ============ FIND PRODUCT BY ID ============
    public Product findProductById(String id) {
        for (Product p : inventory) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null; // Not found
    }

    // ============ DELETE PRODUCT ============
    public void deleteProduct(String id) {
        Product found = findProductById(id);
        if (found != null) {
            inventory.remove(found);
            System.out.println("Success: Product '" + found.getName() + "' deleted.");
        } else {
            System.out.println("Error: Product with ID " + id + " not found.");
        }
    }

    // ============ UPDATE PRODUCT ============
    public void updateProduct(String id, double newPrice, int newQty) {
        Product found = findProductById(id);
        if (found != null) {
            found.setPrice(newPrice);
            found.setQuantity(newQty);
            System.out.println("Success: Product '" + found.getName() + "' updated.");
        } else {
            System.out.println("Error: Product with ID " + id + " not found.");
        }
    }

    // ============ PROCESS SALE (CHECKOUT) ============
    public void processSale(Scanner scanner) {
        double total = 0.0;
        ArrayList<Product> cart = new ArrayList<>();
        ArrayList<Integer> cartQuantities = new ArrayList<>();

        System.out.println("\n===== NEW SALE =====");
        System.out.println("Enter product IDs one by one. Type 'done' when finished.");

        while (true) {
            System.out.print("Enter Product ID (or 'done'): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;

            Product p = findProductById(input);
            if (p == null) {
                System.out.println("Product not found.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();

            if (qty > p.getQuantity()) {
                System.out.println("Not enough stock! Available: " + p.getQuantity());
                continue;
            }

            // Add to cart and reduce stock
            p.setQuantity(p.getQuantity() - qty);
            cart.add(p);
            cartQuantities.add(qty);
            total += p.getPrice() * qty;
            System.out.println("Added: " + qty + " x " + p.getName());
        }

        if (cart.isEmpty()) {
            System.out.println("Sale cancelled - no items.");
            return;
        }

        // Apply discount if total > $100
        double discount = 0;
        if (total > 100) {
            discount = total * 0.10;
            total -= discount;
        }

        // Print receipt

        // Build receipt string (for both printing and saving)
        StringBuilder receipt = new StringBuilder();
        receipt.append("\n===== RECEIPT =====\n");

        for (int i = 0; i < cart.size(); i++) {
            Product p = cart.get(i);
            int q = cartQuantities.get(i);
            String line = String.format("%-15s x%d @ $%.2f = $%.2f%n", p.getName(), q, p.getPrice(), q * p.getPrice());
            System.out.print(line);
            receipt.append(line);
        }

        if (discount > 0) {
            String discLine = String.format("Discount (10%%): -$%.2f%n", discount);
            System.out.print(discLine);
            receipt.append(discLine);
        }

        String totalLine = String.format("TOTAL: $%.2f%n", total);
        System.out.print(totalLine);
        System.out.println("===================");

        receipt.append(totalLine);
        receipt.append("Date: ").append(new java.util.Date()).append("\n");

        // Save the receipt to file
        FileHandler.saveSaleReceipt(receipt.toString());
        System.out.println("Receipt saved to sales_history.txt");
    }

    // ============ LOW STOCK REPORT ============
    public void lowStockReport() {
        System.out.println("\n===== LOW STOCK REPORT (< 10 units) =====");
        boolean found = false;
        for (Product p : inventory) {
            if (p.getQuantity() < 10) {
                System.out.println(p.toString());
                found = true;
            }
        }
        if (!found) System.out.println("All products have sufficient stock.");
    }

    // ============ INVENTORY VALUE REPORT ============
    public void inventoryValueReport() {
        double totalValue = 0;
        for (Product p : inventory) {
            totalValue += p.getPrice() * p.getQuantity();
        }
        System.out.printf("\nTotal Inventory Value: $%.2f%n", totalValue);
        System.out.println("Total number of products: " + inventory.size());
    }


    // ============ SEARCH BY NAME (partial match) ============
    public void searchByName(String keyword) {
        System.out.println("\n===== SEARCH RESULTS FOR: " + keyword + " =====");
        boolean found = false;
        for (Product p : inventory) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(p.toString());
                found = true;
            }
        }
        if (!found) System.out.println("No products matched.");
    }

    // ============ FILTER BY CATEGORY ============
    public void filterByCategory(String category) {
        System.out.println("\n===== PRODUCTS IN CATEGORY: " + category + " =====");
        boolean found = false;
        for (Product p : inventory) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                System.out.println(p.toString());
                found = true;
            }
        }
        if (!found) System.out.println("No products in this category.");
    }

    // ============ RESTOCK EXISTING PRODUCT ============
    public void restockProduct(String id, int amount) {
        Product found = findProductById(id);
        if (found != null) {
            found.setQuantity(found.getQuantity() + amount);
            System.out.println("Success: Added " + amount + " units to '" + found.getName()
                    + "'. New stock: " + found.getQuantity());
        } else {
            System.out.println("Error: Product with ID " + id + " not found.");
        }
    }

    // ============ SORT PRODUCTS BY PRICE ============
    public void sortByPrice() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        ArrayList<Product> sorted = new ArrayList<>(inventory);
        sorted.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        System.out.println("\n===== PRODUCTS SORTED BY PRICE (Low → High) =====");
        for (Product p : sorted) {
            System.out.println(p.toString());
        }
    }

    // ============ TOP SELLING PRODUCTS REPORT ============
    public void topSellingReport() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        // Sort by quantity sold (the lower the stock, the more sold)
        ArrayList<Product> sorted = new ArrayList<>(inventory);
        sorted.sort((a, b) -> Integer.compare(a.getQuantity(), b.getQuantity()));
        System.out.println("\n===== TOP SELLING PRODUCTS (Low Stock = High Sales) =====");
        int count = 0;
        for (Product p : sorted) {
            if (count >= 5) break; // Top 5 only
            System.out.println(p.toString());
            count++;
        }
    }

    // ============ GET INVENTORY ============
    public ArrayList<Product> getInventory() {
        return inventory;
    }
}