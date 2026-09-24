package encapsulation;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private static final String INVENTORY_FILE = "inventory.csv";
    private static final String SALES_FILE = "sales_history.txt";

    // ============ SAVE INVENTORY ============
    public static void saveInventory(ArrayList<Product> inventory) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (Product p : inventory) {
                bw.write(p.toCSV());
                bw.newLine();
            }
            System.out.println("Inventory saved (" + inventory.size() + " products).");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    // ============ LOAD INVENTORY ============
    public static ArrayList<Product> loadInventory() {
        ArrayList<Product> inventory = new ArrayList<>();
        File file = new File(INVENTORY_FILE);

        if (!file.exists()) {
            System.out.println("No saved inventory found. Starting fresh.");
            return inventory;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int qty = Integer.parseInt(parts[3]);
                    String category = parts[4];
                    inventory.add(new Product(id, name, price, qty, category));
                }
            }
            System.out.println("Loaded " + inventory.size() + " products from file.");
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
        return inventory;
    }
    // ============ VIEW SALES HISTORY ============
    public static void viewSalesHistory() {
        File file = new File(SALES_FILE);
        if (!file.exists()) {
            System.out.println("No sales history found. Make a sale first!");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n===== SALES HISTORY =====");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("=========================");
        } catch (IOException e) {
            System.out.println("Error reading sales history: " + e.getMessage());
        }
    }

    // ============ SAVE A SALE RECEIPT ============
    public static void saveSaleReceipt(String receipt) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SALES_FILE, true))) {
            bw.write(receipt);
            bw.newLine();
            bw.write("==================================================");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }

    }
}