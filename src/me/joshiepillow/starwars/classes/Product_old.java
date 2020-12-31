package me.joshiepillow.starwars.classes;

import java.util.List;

public class Product_old extends SingleNameObject {
    /**
     * Product_old cost
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
     * @return new Product_old, if name is taken, null
     */
    static Product_old create(String name, int cost, String command) {
        if (isTaken(name, Product_old.class)) return null;
        return new Product_old(name, cost, command);
    }

    /**
     * Unsafe constructor
     * @param name name of product
     * @param cost cost of product
     * @param command command to run
     */
    private Product_old(String name, int cost, String command) {
        super(name);
        this.cost = cost;
        this.command = command;
    }

    /**
     * Finds a Product_old by name
     * @param s name to match to
     * @return Product_old that matches, if none returns null
     */
    public static Product_old getByName(String s) {
        return (Product_old) getByName(s, Product_old.class);
    }

    /**
     * Simple setter
     * @param name name to set to
     * @return if successful
     */
    boolean setName(String name) {
        return setName(name, Product_old.class);
    }

    /**
     * Simple setter
     * @param command command to set to
     */
    void setCommand(String command) {
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
    void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Get list of all Product_olds
     * @return list of all Product_olds
     */
    public static List<Product_old> getAll() {
        return (List<Product_old>)(List<?>)getAll(Product_old.class);
    }

    /**
     * Sets all data and names
     * @param list input from serialized
     */
    public static void setAll(List<Product_old> list) {
        setAll(list, Product_old.class);
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
        erase(Product_old.class);
    }
}
