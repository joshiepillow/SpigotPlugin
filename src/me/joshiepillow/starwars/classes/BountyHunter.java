package me.joshiepillow.starwars.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BountyHunter extends SingleNameObject {
    /**
     * Player username
     */
    private String username;

    /**
     * Money
     */
    private int credits;

    /**
     * Safe constructor
     * @param username player username
     * @param name wanted BountyName
     * @return new BountyHunter, if name is taken, null
     */
    public static BountyHunter create(String username, String name) {
        if (isTaken(name, BountyHunter.class)) return null;
        return new BountyHunter(username, name);
    }

    /**
     * Unsafe constructor
     * @param username player username
     * @param name wanted BountyName
     */
    private BountyHunter(String username, String name) {
        super(name);
        this.username = username;
        credits = 0;
    }

    /**
     * Set Name - Sets "Bounty Name"
     * Get Name not required - inherited
     * @param name new name
     * @return if successful
     */
    public boolean setName(String name) {
        return setName(name, BountyHunter.class);
    }

    /**
     * Simple getter
     * @return number of credits
     */
    public int getBal() {
        return credits;
    }

    /**
     * Changes balance if possible
     * Prevents negative balances
     * @param change positive or negative change
     * @return if successful
     */
    public boolean changeBal(int change) {
        if (credits + change < 0) return false;
        credits += change;
        return true;
    }

    /**
     * Simple toString method
     * @return string
     */
    @Override
    public String toString() {
        return "(" + username + ") " + getName() + " -- " + credits + "C";
    }

    /**
     * Sets all data and names
     * @param list input from serialized
     */
    public static void SetAll(List<BountyHunter> list) {
        setAll(list, BountyHunter.class);
    }

    /**
     * Gets all hunters for storage
     * @return list of all hunters
     */
    public static List<BountyHunter> getAll() {
        return (List<BountyHunter>)(List<?>)getAll(BountyHunter.class);
    }

    /**
     * Finds a bountyhunter by username
     * @param s username to match to
     * @return BountyHunter that matches, if none returns null
     */
    public static BountyHunter getByUsername(String s) {
        s = s.toLowerCase();
        List<BountyHunter> hunters = BountyHunter.getAll();
        for (BountyHunter hunter : hunters) {
            if (hunter.username.toLowerCase().equals(s)) {
                return hunter;
            }
        }
        return null;
    }

    /**
     * Buy a product
     * @param p valid product
     * @return if affordable, command, if unaffordable, null
     */
    public String buy(Product p) {
        if (changeBal(-p.getCost())) return p.getCommand(username);
        return null;
    }
}
