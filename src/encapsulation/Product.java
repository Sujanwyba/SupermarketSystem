package encapsulation; // Keep this line if you moved it to a package

public class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    // CONSTRUCTOR: This lets us create a Product in one line
    public Product(String id, String name, double price, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCategory(String category) { this.category = category; }

    // This method formats the product as a CSV line for saving to a file later
    public String toCSV() {
        return id + "," + name + "," + price + "," + quantity + "," + category;
    }

    // This method formats the product nicely for printing in the console
    @Override
    public String toString() {
        return String.format("ID: %-6s | Name: %-15s | Price: $%-7.2f | Qty: %-5d | Category: %s",
                id, name, price, quantity, category);
    }
}
