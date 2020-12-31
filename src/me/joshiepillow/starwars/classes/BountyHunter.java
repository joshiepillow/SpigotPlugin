package me.joshiepillow.starwars.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BountyHunter extends SingleNameObject {
    /**
     * Player username
     */
    private UUID UUID;

    private List<Quest> quests = new ArrayList<>();

    /**
     * Money
     */
    private int credits;

    private boolean[] isUnlockedForcePowers = {false,false,false,false,false,false,false,false,false};

    public ItemStack Lightsaber; //null if none

    /**
     * Safe constructor
     * @param UUID player UUID
     * @param name wanted BountyName
     * @return new BountyHunter, if name is taken, null
     */
    public static BountyHunter create(UUID UUID, String name) {
        if (name.equals("")) return new BountyHunter(UUID, "");
        if (isTaken(name, BountyHunter.class)) return null;
        return new BountyHunter(UUID, name);
    }

    /**
     * Unsafe constructor
     * @param UUID player UUID
     * @param name wanted BountyName
     */
    private BountyHunter(UUID UUID, String name) {
        super(name);
        this.UUID = UUID;
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

    public String getName() {
        System.out.println(super.getName());
        if (super.getName().equals("")) {
            return Bukkit.getOfflinePlayer(UUID).getName();
        } else {
            return super.getName();
        }
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

    //public String getUsername() {
    //    return username;
    //}

    /**
     * Simple toString method
     * @return string
     */
    @Override
    public String toString() {
        return this.getName() + " -- " + credits + "C";
    }

    /**
     * Sets all data and names
     * @param list input from serialized
     */
    public static void setAll(List<BountyHunter> list) {
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
     * @param UUID UUID to match to
     * @return BountyHunter that matches, if none returns null
     */
    public static BountyHunter getByUUID(UUID UUID) {
        List<BountyHunter> hunters = BountyHunter.getAll();
        for (BountyHunter hunter : hunters) {
            if (hunter.UUID.equals(UUID)) {
                return hunter;
            }
        }
        return null;
    }

    /*public List<String> buy(Product p) {
        if (changeBal(-p.getCost())) {
            List<String> out = new ArrayList<>();
            String s = p.getCommand(Bukkit.getOfflinePlayer(UUID).getName());
            for (String dat : s.split("/")) {
                if (!dat.isEmpty()) {
                    out.add(dat);
                }
            }
            return out;

        }
        return null;
    }*/

    public void erase() {
        erase(BountyHunter.class);
    }

    public void addQuest(String type, String item_name, int count, String complete_command) {
        Quest q = Quest.create(type, item_name, count, complete_command);
        quests.add(q);
    }
    public List<String> submit(Inventory i) {
        List<String> out = new ArrayList<>();
        List<Quest> quest_copy = new ArrayList<>(quests);
        for (Quest datum : quest_copy) {
            String s = datum.submit(i);
            if (s!=null) {
                quests.remove(datum);
                for (String dat : s.replace("%name", Bukkit.getOfflinePlayer(UUID).getName()).split("/")) {
                    if (!dat.isEmpty()) {
                        out.add(dat);
                    }
                }
            }
        }
        return out;
    }
    public List<String> submit(Inventory i, int num) {
        List<String> out = new ArrayList<>();
        Quest q = quests.get(num);
        String s = q.submit(i);
        if (s!=null) {
            quests.remove(num);
            for (String dat : s.replace("%name", Bukkit.getOfflinePlayer(UUID).getName()).split("/")) {
                if (!dat.isEmpty()) {
                    out.add(dat);
                }
            }
        }
        return out;
    }
    public String quests() {
        StringBuilder s = new StringBuilder();
        for (Quest q : quests)
            s.append(" -- ").append(q.toString()).append("\n");
        return s.toString();
    }
    public Inventory QuestGui() {
        /*Inventory inv = Bukkit.createInventory(null, 54, "Quests");
        for (int i = 0; i < quests.size(); i++) {
            Quest q = quests.get(i);
            ItemStack item = Inventories.create("hunter.submit " + Bukkit.getOfflinePlayer(UUID).getName() + " " + i,
                    Material.PLAYER_HEAD, q.getItem_name(), ""+q.getCount());
            SkullMeta m = (SkullMeta) item.getItemMeta();
            m.setOwningPlayer(Bukkit.getOfflinePlayer(
                    "MHF_" + q.getItem_name().replace("_", "")));
            item.setItemMeta(m);
            inv.setItem(i, item);
        }
        Inventories.fillAll(inv);
        return inv;*/
        return null;
    }
    public boolean isUnlockedForcePower(int i) {
        return (isUnlockedForcePowers[i]);
    }
    public boolean unlockForcePower(int i) {
        if (isUnlockedForcePowers[i]) return false;
        isUnlockedForcePowers[i] = true;
        return true;
    }
    public int unlockAllForcePowers() {
        int counter = 0;
        for (int i = 0; i < isUnlockedForcePowers.length; i++) {
            if (unlockForcePower(i)) counter++;
        }
        return counter;
    }
    public boolean lockForcePower(int i) {
        if (!isUnlockedForcePowers[i]) return false;
        isUnlockedForcePowers[i] = false;
        return true;
    }
    public int lockAllForcePowers() {
        int counter = 0;
        for (int i = 0; i < isUnlockedForcePowers.length; i++) {
            if (lockForcePower(i)) counter++;
        }
        return counter;
    }
}
