package me.joshiepillow.starwars.classes;

import java.util.List;

public class Product extends SingleNameObject {
    /**
     * Product cost
     */
    private int cost;

    /**
     * Command to run if bought
     */
    private String command;

    /**
     * Safe constructor
     * @param name name of product
     * @param cost cost of product
     * @param command command to run
     * @return new Product, if name is taken, null
     */
    public static Product create(String name, int cost, String command) {
        if (isTaken(name, Product.class)) return null;
        return new Product(name, cost, command);
    }

    /**
     * Unsafe constructor
     * @param name name of product
     * @param cost cost of product
     * @param command command to run
     */
    private Product(String name, int cost, String command) {
        super(name);
        this.cost = cost;
        this.command = command;
    }

    /**
     * Finds a Product by name
     * @param s name to match to
     * @return Product that matches, if none returns null
     */
    public static Product getByName(String s) {
        return (Product) getByName(s, Product.class);
    }

    /**
     * Simple setter
     * @param name name to set to
     * @return if successful
     */
    public boolean setName(String name) {
        return setName(name, Product.class);
    }

    /**
     * Simple setter
     * @param command command to set to
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Gets command and replaces %name with username
     * @param username replacement
     * @return reformatted string
     */
    public String getCommand(String username) {
        return command.replace("%name", username);
    }

    /**
     * Simple getter
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Simple getter
     * @return cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Simple setter
     * @param cost what to set cost to
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Get list of all Products
     * @return list of all Products
     */
    public static List<Product> getAll() {
        return (List<Product>)(List<?>)getAll(Product.class);
    }

    /**
     * Sets all data and names
     * @param list input from serialized
     */
    public static void SetAll(List<Product> list) {
        setAll(list, Product.class);
    }

    /**
     * Simple toString function
     * @return String
     */
    @Override
    public String toString() {
        return this.getName()+"--"+this.getCost()+"C " + this.getCommand();
    }

    public void erase() {
        erase(Product.class);
    }
}
