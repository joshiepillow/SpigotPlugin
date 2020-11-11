package me.joshiepillow.starwars.classes;

public class Product extends SingleNameObject {
    private int cost;
    private String command;

    public static Product create(String name, int cost, String command) {
        if (isTaken(name, Product.class)) return null;
        return new Product(name, cost, command);
    }

    private Product(String name, int cost, String command) {
        super(name);
        this.cost = cost;
        this.command = command;
    }

    public static Product getByName(String s) {
        return (Product) getByName(s, Product.class);
    }

    public boolean setName(String name) {
        return setName(name, Product.class);
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand(String username) {
        return command.replace("%name", username);
    }

    public String getCommand() {
        return command;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
