package me.joshiepillow.starwars;

import me.joshiepillow.starwars.classes.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BountyHunter implements Serializable {
    private String bountyName;
    private String username;
    private int credits;
    private static List<String> names_taken = new ArrayList<>();
    private static List<BountyHunter> hunters = new ArrayList<>();

    public static BountyHunter create(String username, String bountyName) {
        if (isNameTaken(username)) return null;
        return new BountyHunter(username, bountyName);
    }
    private BountyHunter(String username, String bountyName) {
        if (!setBountyName(bountyName)) throw new IllegalArgumentException("This shouldn't be able to happen. Use BountyHunter.create for safety.");
        this.username = username;
        credits = 0;
        hunters.add(this);
    }

    public boolean setBountyName(String s) {
        if(isNameTaken(s)) return false;
        bountyName = s;
        names_taken.add(s.toLowerCase());
        return true;
    }

    static public boolean isNameTaken(String s) {
        if (names_taken.contains(s.toLowerCase())) return true;
        return false;
    }

    public String getBountyName() {
        return bountyName;
    }

    public int getBal() {
        return credits;
    }

    public boolean changeBal(int change) {
        if (credits + change < 0) return false;
        credits += change;
        return true;
    }

    @Override
    public String toString() {
        return "(" + username + ") " + getBountyName() + " -- " + getBal() + "C";
    }

    public static void SetAll(List<BountyHunter> list) {
        for (int i = 0; i < list.size(); i++) {
            names_taken.add(list.get(i).getBountyName());
            hunters.add(list.get(i));
        }
    }
    public static List<BountyHunter> getAll() {
        return hunters;
    }

    public static BountyHunter getByUsername(String s) {
        s = s.toLowerCase();
        for (int i = 0; i < hunters.size(); i++) {
            if (hunters.get(i).username.toLowerCase().equals(s)) {
                return hunters.get(i);
            }
        }
        return null;
    }

    public String buy(Product p) {
        if (changeBal(-p.getCost())) return p.getCommand(username);
        return null;
    }
}
