package me.joshiepillow.starwars.classes;

import me.joshiepillow.CustomItems.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomItem {
    //fill this with the items later
    //much better than commands
    private static JavaPlugin plugin;

    //Lightsabers
    public static ItemStack GREEN_LIGHTSABER;
    public static ItemStack BLUE_LIGHTSABER;
    public static ItemStack RED_LIGHTSABER;
    public static ItemStack FORCE_ABILITIES_HOLDER;

    public static void setItems(JavaPlugin plugin) {
        CustomItem.plugin = plugin;
        setForceAbilitiesHolder();

        ItemMeta m;

        GREEN_LIGHTSABER = new ItemStack(Material.IRON_SWORD);
        m = GREEN_LIGHTSABER.getItemMeta();
        assert m != null;
        m.setDisplayName("Green Lightsaber");
        m.setLore(new ArrayList<String>() {{
            add("Lightsaber");
            add("Damage: 1");
        }});
        m.addEnchant(Enchantment.CHANNELING, 1, false);
        m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(), "damage", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        GREEN_LIGHTSABER.setItemMeta(m);

        BLUE_LIGHTSABER = new ItemStack(Material.IRON_AXE);
        m = BLUE_LIGHTSABER.getItemMeta();
        assert m != null;
        m.setDisplayName("Blue Lightsaber");
        m.setLore(new ArrayList<String>() {{
            add("Lightsaber");
            add("Damage: 1");
        }});
        m.addEnchant(Enchantment.CHANNELING, 1, false);
        m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(), "damage", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        BLUE_LIGHTSABER.setItemMeta(m);

        RED_LIGHTSABER = new ItemStack(Material.DIAMOND_SWORD);
        m = RED_LIGHTSABER.getItemMeta();
        assert m != null;
        m.setLore(new ArrayList<String>() {{
            add("Lightsaber");
            add("Damage: 1");
        }});
        m.addEnchant(Enchantment.CHANNELING, 1, false);
        m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(), "damage", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        m.setDisplayName("Red Lightsaber");
        RED_LIGHTSABER.setItemMeta(m);
    }
    public static List<UUID> ForceLand = new ArrayList<>();
    private static void setForceAbilitiesHolder() {

        FORCE_ABILITIES_HOLDER = new ItemStack(Material.STONE);
        ItemMeta meta = FORCE_ABILITIES_HOLDER.getItemMeta();
        meta.setDisplayName("§5Force Abilities");
        meta.setLore(new ArrayList<String>(){{add("§6Click anywhere to use.");}});
        FORCE_ABILITIES_HOLDER.setItemMeta(meta);
        CustomItems customItems = new CustomItems();
        Inventory inv = Bukkit.createInventory(null, 9);
        HashMap<UUID, Inventory> player_invs = new HashMap<>();

        //define a custom function which will help later
        int cool_down = 200; //cooldown time
        CustomItems cooldown = new CustomItems();
        cooldown.onInventoryClickEvent = (event2) -> { //prevent player from taking item
            event2.setCancelled(true);
            ((Player) event2.getWhoClicked()).updateInventory();
        };
        ItemStack cooldown_item = cooldown.setPersistentData(new ItemStack(Material.RED_STAINED_GLASS_PANE));
        Runnable r = () -> {
            ItemStack[] items = new ItemStack[inv.getSize()];
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) != null) {
                    items[i] = inv.getItem(i);
                    inv.setItem(i, cooldown_item);
                }
            }
            AtomicInteger counter = new AtomicInteger();
            int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                for (int i = 0; i < inv.getSize(); i++) {
                    if (inv.getItem(i) != null) {
                        ItemMeta m = inv.getItem(i).getItemMeta();
                        m.setDisplayName("§4"+(cool_down/20-counter.get())+" second(s) left");
                        inv.getItem(i).setItemMeta(m);

                    }
                }
                counter.getAndIncrement();
            }, 0, 20);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
               for (int i = 0; i < inv.getSize(); i++) inv.setItem(i, items[i]);
               Bukkit.getScheduler().cancelTask(id);
            }, cool_down);
        };

        customItems.onPlayerInteractEvent = event -> {
            if (!player_invs.containsKey(event.getPlayer().getUniqueId())) {
                //reused definitions
                CustomItems _c;
                ItemStack _i;
                ItemMeta _m;

                //FORCE PUSH
                double push_power = 0.3; //adjustable
                double push_radius = 8; //adjustable
                int delay_between_pushes = 5; //adjustable

                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    Player p = (Player) event1.getWhoClicked(); //get clicker
                    p.closeInventory(); //close their inv
                    List<Entity> e = p.getWorld().getEntities(); //get all entities
                    List<Entity> close_e = new ArrayList<>();
                    e.remove(p); //remove the player who clicked
                    for (Entity entity : e) {
                        if (entity.getLocation().toVector().isInSphere(p.getLocation().toVector(), push_radius)) //get all entities within radius
                            close_e.add(entity);
                    }
                    int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                        Vector v = p.getLocation().toVector();
                        close_e.forEach((entity -> {
                            Vector result = entity.getLocation().toVector().multiply(-1); //calc vector
                            result.add(v).normalize(); //unit vector
                            entity.setVelocity(result.multiply(-push_power)); //apply velocity

                        }));
                    }, 0, delay_between_pushes); //run every few ticks
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        Bukkit.getScheduler().cancelTask(i); //cancel after 5 seconds
                    }, 100);

                    r.run(); //cooldown

                    p.updateInventory(); //remove ghost items
                };
                _i = new ItemStack(Material.BLACK_WOOL);
                _m = _i.getItemMeta();
                _m.setDisplayName("§8Force Push");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                //FORCE PULL
                double pull_power = push_power; //adjustable
                double pull_radius = push_radius; //adjustable
                int delay_between_pulls = delay_between_pushes; //adjustable

                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    Player p = (Player) event1.getWhoClicked();
                    p.closeInventory();
                    List<Entity> e = p.getWorld().getEntities();
                    List<Entity> close_e = new ArrayList<>();
                    e.remove(p);
                    for (Entity entity : e) {
                        if (entity.getLocation().toVector().isInSphere(p.getLocation().toVector(), pull_radius))
                            close_e.add(entity);
                    }
                    int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                        Vector v = p.getLocation().toVector();
                        close_e.forEach((entity -> {
                            Vector result = entity.getLocation().toVector().multiply(-1);
                            result.add(v).normalize();
                            entity.setVelocity(result.multiply(pull_power));

                        }));
                    }, 0, delay_between_pulls);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        Bukkit.getScheduler().cancelTask(i);
                    }, 100);

                    r.run(); //cooldown

                    p.updateInventory();
                };
                _i = new ItemStack(Material.WHITE_WOOL);
                _m = _i.getItemMeta();
                _m.setDisplayName("§fForce Pull");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                //FORCE JUMP
                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    event1.getWhoClicked().closeInventory();
                    event1.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 2));

                    r.run(); //cooldown

                    if (event1.getWhoClicked() instanceof Player)
                        ((Player) event1.getWhoClicked()).updateInventory();
                };
                _i = new ItemStack(Material.IRON_BOOTS);
                _m = _i.getItemMeta();
                _m.setDisplayName("§2Force Leap");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                //FORCE SPEED
                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    event1.getWhoClicked().closeInventory();
                    event1.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));

                    r.run(); //cooldown

                    if (event1.getWhoClicked() instanceof Player)
                        ((Player) event1.getWhoClicked()).updateInventory();
                };
                _i = new ItemStack(Material.DIAMOND_BOOTS);
                _m = _i.getItemMeta();
                _m.setDisplayName("§3Force Sprint");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                //FORCE LAND
                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    event1.getWhoClicked().closeInventory();
                    ForceLand.add(event1.getWhoClicked().getUniqueId());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        ForceLand.remove(event1.getWhoClicked().getUniqueId());
                    }, 100);

                    r.run(); //cooldown

                    if (event1.getWhoClicked() instanceof Player)
                        ((Player) event1.getWhoClicked()).updateInventory();
                };
                _i = new ItemStack(Material.NETHERITE_BOOTS);
                _m = _i.getItemMeta();
                _m.setDisplayName("§1Force Land");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                //FORCE CHOKE
                double choke_power = 0.2;
                int choke_distance = 8;
                int delay_between_chokes = 1; //adjustable

                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    Player p = (Player) event1.getWhoClicked();
                    p.closeInventory();
                    Vector step = p.getLocation().getDirection().multiply(0.5); //step in the direction the player is looking
                    List<Entity> e = p.getWorld().getEntities(); // get all entities
                    List<Entity> line_of_sight = new ArrayList<>(); //entities in line of sight
                    Vector loc = p.getLocation().toVector();
                    for (int i = 0; i < choke_distance*2; i++) {
                        loc.add(step);
                        int j = 0;
                        while (j < e.size()) {
                            if (e.get(j).getLocation().toVector().isInSphere(loc, 1)) {
                                line_of_sight.add(e.get(j));
                                e.remove(j);
                            }
                            j++;
                        }
                    }
                    line_of_sight.remove(p);
                    int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()->{
                        for (Entity entity : line_of_sight) {
                            Vector result = event1.getWhoClicked().getLocation().toVector();
                            result.add(event1.getWhoClicked().getLocation().getDirection().multiply(5)); //get goal
                            result.subtract(entity.getLocation().toVector());
                            entity.setVelocity(result.multiply(choke_power));
                        }
                    }, 0, delay_between_chokes);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
                        Bukkit.getScheduler().cancelTask(i);
                    }, 100);

                    r.run(); //cooldown

                    p.updateInventory();
                };
                _i = new ItemStack(Material.BLACK_GLAZED_TERRACOTTA);
                _m = _i.getItemMeta();
                _m.setDisplayName("§4Force Choke");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                //FORCE ABSORPTION
                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    event1.getWhoClicked().closeInventory();
                    event1.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 4));

                    r.run(); //cooldown

                    if (event1.getWhoClicked() instanceof Player)
                        ((Player) event1.getWhoClicked()).updateInventory();
                };
                _i = new ItemStack(Material.GLOWSTONE_DUST);
                _m = _i.getItemMeta();
                _m.setDisplayName("§6Force Bubble");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                //FORCE STRENGTH
                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    event1.getWhoClicked().closeInventory();
                    event1.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));

                    r.run(); //cooldown

                    if (event1.getWhoClicked() instanceof Player)
                        ((Player) event1.getWhoClicked()).updateInventory();
                };
                _i = new ItemStack(Material.REDSTONE);
                _m = _i.getItemMeta();
                _m.setDisplayName("§cForce Scream");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                event.getPlayer().openInventory(inv);
                event.setCancelled(true);

                player_invs.put(event.getPlayer().getUniqueId(), inv);

                //FORCE LIGHTNING
                int delay_between_strikes = 26;

                _c = new CustomItems();
                _c.onInventoryClickEvent = event1 -> {
                    Player p = (Player) event1.getWhoClicked();
                    p.closeInventory();
                    int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                        Location loc = p.getLocation();
                        List<Entity> e = p.getWorld().getEntities(); // get all entities
                        Vector step = p.getLocation().getDirection().multiply(0.5); //step in the direction the player is looking
                        e.remove(p);
                        Entity first = null;
                        Location l = null;
                        outerloop:
                        for (int i = 0; i < choke_distance*2; i++) {
                            loc.add(step);
                            for (int j = 0; j < e.size(); j++) {
                                if (e.get(j).getLocation().toVector().isInSphere(loc.toVector(), 1)) {
                                    first = e.get(j);
                                    break outerloop;
                                }
                            }
                            if (l == null && !p.getWorld().getBlockAt(loc).isPassable()) l = loc;
                        }
                        if (first != null) {
                            p.getWorld().strikeLightning(first.getLocation());
                        } else if (l != null) {
                            p.getWorld().strikeLightning(loc);
                        }
                    }, 20, delay_between_strikes);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        Bukkit.getScheduler().cancelTask(id);
                    }, 100);

                    r.run(); //cooldown

                    p.updateInventory();
                };
                _i = new ItemStack(Material.END_ROD);
                _m = _i.getItemMeta();
                _m.setDisplayName("§5Force Lightning");
                _i.setItemMeta(_m);
                inv.addItem(_c.setPersistentData(_i));

                event.getPlayer().openInventory(inv);
                event.setCancelled(true);

                player_invs.put(event.getPlayer().getUniqueId(), inv);

            } else {
                event.getPlayer().openInventory(player_invs.get(event.getPlayer().getUniqueId()));
                event.setCancelled(true);
            }
        };
        customItems.setPersistentData(FORCE_ABILITIES_HOLDER);
    }
}
