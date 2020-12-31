package me.joshiepillow.starwars.classes;

import me.joshiepillow.CustomItems.CustomItems;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class CustomItem {
    //fill this with the items later
    //much better than commands
    private static JavaPlugin plugin;

    private static CustomItems simpleCustomItem;
    private static CustomItems lightsaberPartCustomItem;

    //Lightsabers
    public static ItemStack GREEN_LIGHTSABER;
    public static ItemStack BLUE_LIGHTSABER;
    public static ItemStack RED_LIGHTSABER;
    public static ItemStack WHITE_LIGHTSABER;
    public static ItemStack YELLOW_LIGHTSABER;
    public static ItemStack PURPLE_LIGHTSABER;
    public static ItemStack DARKSABER;

    //Force stuffs
    public static ItemStack FORCE_ABILITIES_HOLDER;

    //Custom Melee Weapons
    public static ItemStack GADERFFII;
    public static ItemStack GAMMOREAN_AXE;
    public static ItemStack MYTHOSAUR_AXE;
    public static ItemStack KYUSO_PETAR;
    public static ItemStack PURPLE_ELECTRO_STAFF;
    public static ItemStack YELLOW_ELECTRO_STAFF;
    public static ItemStack VIBRO_BLADE_1;
    public static ItemStack VIBRO_BLADE_2;
    public static ItemStack VIBRO_BLADE_3;

    //Custom Lightsaber Parts
    public static ItemStack TOP_HILT;
    public static ItemStack ACTIVATION_STUD;
    public static ItemStack BLADE_EMITTER;
    public static ItemStack BOTTOM_HILT;

    //Custom Armor
    public static ItemStack JEDI_CAP;
    public static ItemStack JEDI_TUNIC;
    public static ItemStack JEDI_PANTS;
    public static ItemStack JEDI_BOOTS;
    public static ItemStack CG_HELMET;
    public static ItemStack CG_CHESTPLATE;
    public static ItemStack CG_LEGGINGS;
    public static ItemStack CG_BOOTS;
    public static ItemStack _501TH_HELMET;
    public static ItemStack _501TH_CHESTPLATE;
    public static ItemStack _501TH_LEGGINGS;
    public static ItemStack _501TH_BOOTS;
    public static ItemStack DURASTEEL_HELMET;
    public static ItemStack DURASTEEL_CHESTPLATE;
    public static ItemStack DURASTEEL_LEGGINGS;
    public static ItemStack DURASTEEL_BOOTS;
    public static ItemStack BESKAR_HELMET;
    public static ItemStack BESKAR_CHESTPLATE;
    public static ItemStack BESKAR_LEGGINGS;
    public static ItemStack BESKAR_BOOTS;

    public static void setItems(JavaPlugin plugin) {
        CustomItem.plugin = plugin;
        setSimpleCustomItem();
        setForceAbilitiesHolder();
        setLightsabers();
        setCustomMelee();
    }
    private static void fillAll(CustomItems c) {
        //prevent dropping, putting in chest, don't drop on death
        c.onPlayerDropItemEvent = event -> event.setCancelled(true);
        c.onEntityDeathEvent = (event, itemStack) -> event.getDrops().remove(itemStack);
        c.onInventoryClickEvent = event -> {
            if (!(event.getWhoClicked().getOpenInventory().getType() == InventoryType.CRAFTING ||
                    event.getWhoClicked().getOpenInventory().getType() == InventoryType.CREATIVE)) {
                event.setCancelled(true);
            }
        };
        c.onBlockPlaceEvent = event -> event.setCancelled(true);
    }
    private static void setSimpleCustomItem() {
        simpleCustomItem = new CustomItems();
        fillAll(simpleCustomItem);
    }
    public static List<UUID> ForceLand = new ArrayList<>();
    private static void setForceAbilitiesHolder() {

        FORCE_ABILITIES_HOLDER = new ItemStack(Material.GRAY_STAINED_GLASS);
        ItemMeta meta = FORCE_ABILITIES_HOLDER.getItemMeta();
        meta.setDisplayName("§5Force Abilities");
        meta.setLore(new ArrayList<String>(){{add("§6Click anywhere to use.");}});
        FORCE_ABILITIES_HOLDER.setItemMeta(meta);
        CustomItems customItems = new CustomItems();
        HashMap<UUID, Inventory> player_invs = new HashMap<>();
        List<ItemStack> storage = Arrays.asList(new ItemStack[9]);

        //define a custom function which will help later
        int cool_down = 200; //cooldown time

        CustomItems locked = new CustomItems();
        locked.onInventoryClickEvent = (event2) -> { //prevent player from taking item
            event2.setCancelled(true);
            ((Player) event2.getWhoClicked()).updateInventory();
        };
        ItemStack lockedItem = locked.setPersistentData(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        meta = lockedItem.getItemMeta();
        meta.setDisplayName("§8Locked");
        lockedItem.setItemMeta(meta);

        Consumer<InventoryClickEvent> r = (event) -> {
            Consumer<PlayerInteractEvent> old_interact = customItems.onPlayerInteractEvent;
            AtomicInteger counter = new AtomicInteger();
            customItems.onPlayerInteractEvent = (event1) -> {
                event1.getPlayer().sendMessage("§4" + (cool_down / 20 - counter.get()) + " second(s) left");
                event1.setCancelled(true);
            };
            int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                /*
                ItemMeta m = FORCE_ABILITIES_HOLDER.getItemMeta();
                m.setDisplayName("§4" + (cool_down / 20 - counter.get()) + " second(s) left");
                FORCE_ABILITIES_HOLDER.setItemMeta(m);
                */
                counter.getAndIncrement();
            }, 0, 20);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
                /*
                ItemMeta m = FORCE_ABILITIES_HOLDER.getItemMeta();
                m.setDisplayName("§5Force Abilities");
                FORCE_ABILITIES_HOLDER.setItemMeta(m);
                Bukkit.getScheduler().cancelTask(id);
                 */
                customItems.onPlayerInteractEvent = old_interact;
            }, cool_down);
        };

        //FORCE PUSH - This is literally useless
        int push_radius = 8; //adjustable
        double push_power = 0.5; //adjustable
        int delay_between_pushes = 5; //adjustable
        storage.set(0, setForcePush(r, push_radius, push_power, delay_between_pushes));

        //FORCE PULL - Also pretty useless
        int pull_radius = push_radius; //adjustable
        double pull_power = push_power; //adjustable
        int delay_between_pulls = delay_between_pushes; //adjustable
        storage.set(1, setForcePull(r, pull_radius, pull_power, delay_between_pulls));

        //FORCE JUMP - Now you can go out of bounds yay!
        storage.set(2, setForceJump(r));

        //FORCE SPEED - We would like you to perform a drug test.
        storage.set(3, setForceSpeed(r));

        //FORCE LAND - Feather falling 1000000000000
        storage.set(4, setForceLand(r));

        //FORCE CHOKE - Cough cough covid
        int choke_distance = pull_radius; //adjustable
        double choke_power = 0.3; //adjustable
        int delay_between_chokes = 1; //adjustable
        storage.set(5, setForceChoke(r, choke_distance, choke_power, delay_between_chokes));

        //FORCE ABSORPTION - Pretty OP ngl
        storage.set(6, setForceAbsorption(r));

        //FORCE STRENGTH - You have equipped the Key of Reason
        storage.set(7, setForceStrength(r));

        //FORCE LIGHTNING - Lightning always strikes twice
        int lightning_distance = 8; //adjustable
        int delay_between_strikes = 26; //adjustable
        storage.set(8, setForceLightning(r, lightning_distance, delay_between_strikes));

        customItems.onPlayerInteractEvent = event -> {
            if (event.getPlayer().getOpenInventory().getType() == InventoryType.CRAFTING ||
                    event.getPlayer().getOpenInventory().getType() == InventoryType.CREATIVE) { //fix weird interaction with shop
                if (!player_invs.containsKey(event.getPlayer().getUniqueId())) {
                    Inventory inv = Bukkit.createInventory(null, 9, "§5Force Abilities");
                    BountyHunter h = BountyHunter.getByUUID(event.getPlayer().getUniqueId());
                    if (h != null)
                        for (int i = 0; i < inv.getSize(); i++) {
                            if (h.isUnlockedForcePower(i)) inv.setItem(i, storage.get(i));
                            else inv.setItem(i, lockedItem);
                        }
                    else
                        for (int i = 0; i < inv.getSize(); i++) inv.setItem(i, lockedItem);
                    event.getPlayer().openInventory(inv);
                    event.setCancelled(true);
                    player_invs.put(event.getPlayer().getUniqueId(), inv);
                } else {
                    Inventory inv = player_invs.get(event.getPlayer().getUniqueId());
                    BountyHunter h = BountyHunter.getByUUID(event.getPlayer().getUniqueId());
                    if (h != null)
                        for (int i = 0; i < inv.getSize(); i++) {
                            if (inv.getItem(i).getType() != Material.RED_STAINED_GLASS_PANE) {
                                if (h.isUnlockedForcePower(i)) inv.setItem(i, storage.get(i));
                                else inv.setItem(i, lockedItem);
                            }
                        }
                    else
                        for (int i = 0; i < inv.getSize(); i++) inv.setItem(i, lockedItem);
                    event.getPlayer().openInventory(inv);
                    event.setCancelled(true);
                }
            }
        };
        fillAll(customItems);
        customItems.setPersistentData(FORCE_ABILITIES_HOLDER);
    }
    private static void makeParticleCircle(Location loc, Color color) {
        int number_of_particles = 64;
        double speed = 0.2;
        int time = 10;
        AtomicInteger a = new AtomicInteger();
        int id2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()-> {
            for (int i = 0; i < number_of_particles; i++) {
                Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
                Location l = loc.clone();
                loc.getWorld().spawnParticle(Particle.REDSTONE,
                        l.add(new Vector(Math.cos(2*Math.PI*i/(double)number_of_particles), 0, Math.sin(2*Math.PI*i/(double)number_of_particles))
                                .multiply(a.get()*speed)), 1, dustOptions);
            }
            a.getAndIncrement();
        }, 0,1);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            Bukkit.getScheduler().cancelTask(id2);
        }, time);
    }
    private static void lerpParticles(Location a, Location b, Color color) {
        int number_of_particles = 16;
        Vector v = b.toVector().subtract(a.toVector()).multiply(1.0/number_of_particles);
        for (int i = 0; i < number_of_particles; i++) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
            a.add(v);
            a.getWorld().spawnParticle(Particle.REDSTONE, a, 1, dustOptions);
        }
    }
    private static ItemStack setForcePush(Consumer r, int push_radius, double push_power, int delay_between_pushes) {
        CustomItems _c = new CustomItems();
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
                close_e.forEach(entity -> {
                    Vector result = entity.getLocation().toVector().multiply(-1); //calc vector
                    result.add(v).normalize(); //unit vector
                    entity.setVelocity(result.multiply(-push_power)); //apply velocity

                });
            }, 0, delay_between_pushes); //run every few ticks

            int i2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> { //particle lines
                close_e.forEach(entity -> {
                    lerpParticles(p.getLocation().add(new Vector(0,1,0)), entity.getLocation(), Color.fromRGB(85, 85, 85));//draw line
                });
            }, 0, 1);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.getScheduler().cancelTask(i); //cancel after 5 seconds
                Bukkit.getScheduler().cancelTask(i2);
            }, 100);

            //Add particle ring effect
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(85, 85, 85));

            r.accept(event1); //cooldown

            p.updateInventory(); //remove ghost items
        };
        ItemStack _i = new ItemStack(Material.BLACK_WOOL);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§8Force Push");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForcePull(Consumer r, int pull_radius, double pull_power, int delay_between_pulls) {
        CustomItems _c = new CustomItems();
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
                close_e.forEach(entity -> {
                    Vector result = entity.getLocation().toVector().multiply(-1);
                    result.add(v).normalize();
                    entity.setVelocity(result.multiply(pull_power));

                });
            }, 0, delay_between_pulls);
            int i2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> { //particle lines
                close_e.forEach(entity -> {
                    lerpParticles(p.getLocation().add(new Vector(0,1,0)), entity.getLocation(), Color.fromRGB(255, 255, 255));//draw line
                });
            }, 0, 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.getScheduler().cancelTask(i);
                Bukkit.getScheduler().cancelTask(i2);
            }, 100);

            //Add particle ring effect
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(255, 255, 255));

            r.accept(event1); //cooldown

            p.updateInventory();
        };
        ItemStack _i = new ItemStack(Material.WHITE_WOOL);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§fForce Pull");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForceJump(Consumer r) {
        CustomItems _c = new CustomItems();
        _c.onInventoryClickEvent = event1 -> {
            Player p = (Player) event1.getWhoClicked();
            p.closeInventory();

            //Add particle ring effect
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 2));
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(0, 170, 0));

            r.accept(event1); //cooldown

            if (event1.getWhoClicked() instanceof Player)
                ((Player) event1.getWhoClicked()).updateInventory();
        };
        ItemStack _i = new ItemStack(Material.IRON_BOOTS);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§2Force Leap");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForceSpeed(Consumer r) {
        CustomItems _c = new CustomItems();
        _c.onInventoryClickEvent = event1 -> {
            event1.getWhoClicked().closeInventory();
            event1.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));

            //Add particle ring effect
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(0, 170, 170));

            r.accept(event1); //cooldown

            if (event1.getWhoClicked() instanceof Player)
                ((Player) event1.getWhoClicked()).updateInventory();
        };
        ItemStack _i = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§3Force Sprint");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForceLand(Consumer r) {
        CustomItems _c = new CustomItems();
        _c.onInventoryClickEvent = event1 -> {
            event1.getWhoClicked().closeInventory();
            ForceLand.add(event1.getWhoClicked().getUniqueId());
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                ForceLand.remove(event1.getWhoClicked().getUniqueId());
            }, 100);

            //Add particle ring effect
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(0, 0, 170));

            r.accept(event1); //cooldown

            if (event1.getWhoClicked() instanceof Player)
                ((Player) event1.getWhoClicked()).updateInventory();
        };
        ItemStack _i = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§1Force Land");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForceChoke(Consumer r, int choke_distance, double choke_power, int delay_between_chokes) {
        CustomItems _c = new CustomItems();
        _c.onInventoryClickEvent = event1 -> {
            Player p = (Player) event1.getWhoClicked();
            p.closeInventory();
            List<Entity> line_of_sight = new ArrayList<>(); //entities in line of sight
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> { //give the player some time to adjust direction
                Vector step = p.getLocation().getDirection().multiply(0.5); //step in the direction the player is looking
                List<Entity> e = p.getWorld().getEntities(); // get all entities
                Vector loc = p.getLocation().toVector();
                for (int i = 0; i < choke_distance * 2; i++) {
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

                int i2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> { //particle lines
                    line_of_sight.forEach(entity -> {
                        lerpParticles(p.getLocation().add(new Vector(0,1,0)), entity.getLocation(), Color.fromRGB(170, 0, 0));//draw line
                    });
                }, 0, 1);

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ()->{
                    Bukkit.getScheduler().cancelTask(i);
                    Bukkit.getScheduler().cancelTask(i2);
                }, 100-20);
            }, 20);

            //Add particle ring effect
            Location loc2 = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc2, Color.fromRGB(170, 0, 0));

            r.accept(event1); //cooldown

            p.updateInventory();
        };
        ItemStack _i = new ItemStack(Material.BLACK_GLAZED_TERRACOTTA);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§4Force Choke");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForceAbsorption(Consumer r) {
        CustomItems _c = new CustomItems();
        _c.onInventoryClickEvent = event1 -> {
            event1.getWhoClicked().closeInventory();
            event1.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 4));

            //Add particle ring effect
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(255, 170, 0));

            r.accept(event1); //cooldown

            if (event1.getWhoClicked() instanceof Player)
                ((Player) event1.getWhoClicked()).updateInventory();
        };
        ItemStack _i = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§6Force Bubble");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForceStrength(Consumer r) {
        CustomItems _c = new CustomItems();
        _c.onInventoryClickEvent = event1 -> {
            event1.getWhoClicked().closeInventory();
            event1.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));

            //Add particle ring effect
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(255, 85, 85));

            r.accept(event1); //cooldown

            if (event1.getWhoClicked() instanceof Player)
                ((Player) event1.getWhoClicked()).updateInventory();
        };
        ItemStack _i = new ItemStack(Material.IRON_SWORD);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§cForce Scream");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static ItemStack setForceLightning(Consumer r, int lightning_distance, int delay_between_strikes) {
        CustomItems _c = new CustomItems();

        _c.onInventoryClickEvent = event1 -> {
            Player p = (Player) event1.getWhoClicked();
            p.closeInventory();
            int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                Location loc = p.getLocation();
                p.getWorld().setStorm(true);
                List<Entity> e = p.getWorld().getEntities(); // get all entities
                Vector step = p.getLocation().getDirection().multiply(0.5); //step in the direction the player is looking
                e.remove(p);
                Entity first = null;
                Location l = null;
                outerloop:
                for (int i = 0; i < lightning_distance*2; i++) {
                    loc.add(step);
                    for (int j = 0; j < e.size(); j++) {
                        if (e.get(j).getLocation().toVector().isInSphere(loc.toVector(), 1)) {
                            first = e.get(j);
                            break outerloop;
                        }
                    }
                    if (l == null && !p.getWorld().getBlockAt(loc).isPassable()) l = p.getWorld().getBlockAt(loc).getLocation();
                }
                if (first != null) {
                    p.getWorld().strikeLightning(first.getLocation());
                } else if (l != null) {
                    p.getWorld().strikeLightning(l.add(new Vector(0,1,0)));
                }
            }, 20, delay_between_strikes);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                p.getWorld().setStorm(false);
                Bukkit.getScheduler().cancelTask(id);
            }, 100);

            //Add particle ring effect
            Location loc = event1.getWhoClicked().getLocation().add(new Vector(0,1,0));
            makeParticleCircle(loc, Color.fromRGB(170, 0, 170));

            r.accept(event1); //cooldown

            p.updateInventory();
        };
        ItemStack _i = new ItemStack(Material.END_ROD);
        ItemMeta _m = _i.getItemMeta();
        _m.setDisplayName("§5Force Lightning");
        _i.setItemMeta(_m);
        return _c.setPersistentData(_i);
    }
    private static void setLightsabers() {
        setLightsaberPartCustomItem();

        TOP_HILT = lightsaberPartsBuilder("§1Top Hilt", Material.BROWN_MUSHROOM);
        ACTIVATION_STUD = lightsaberPartsBuilder("§1Activation Stud", Material.GOLD_NUGGET);
        BLADE_EMITTER = lightsaberPartsBuilder("§1Blade Emitter", Material.IRON_NUGGET);
        BOTTOM_HILT = lightsaberPartsBuilder("§1Bottom Hilt", Material.RED_MUSHROOM);

        GREEN_LIGHTSABER = setCustomHelper("§aGreen Lightsaber", Material.DIAMOND_SWORD,10, 2, 222222);
        BLUE_LIGHTSABER = setCustomHelper("§9Blue Lightsaber", Material.DIAMOND_SWORD,10, 2, 333333);
        RED_LIGHTSABER = setCustomHelper("§4Red Lightsaber", Material.DIAMOND_SWORD,10, 2, 444444);
        WHITE_LIGHTSABER = setCustomHelper("§fWhite Lightsaber", Material.DIAMOND_SWORD,10, 2, 555555); //this one is special (needs               extra stuff to make)
        YELLOW_LIGHTSABER = setCustomHelper("§eYellow Lightsaber", Material.DIAMOND_SWORD,10, 2, 666666);
        PURPLE_LIGHTSABER = setCustomHelper("§5Purple Lightsaber", Material.DIAMOND_SWORD,10, 2, 777777);
        DARKSABER = setCustomHelper("§0Darksaber", Material.NETHERITE_SWORD,12, 2, 111111); //this is also special (needs                          other things as well
    }
    private static void setLightsaberPartCustomItem() {
        lightsaberPartCustomItem = new CustomItems();
        fillAll(lightsaberPartCustomItem);
        lightsaberPartCustomItem.onInventoryClickEvent = event -> {
            if (!(event.getWhoClicked().getOpenInventory().getType() == InventoryType.CRAFTING ||
                    event.getWhoClicked().getOpenInventory().getType() == InventoryType.CREATIVE ||
                    event.getWhoClicked().getOpenInventory().getType() == InventoryType.WORKBENCH)) {
                event.setCancelled(true);
            }
        };
    }
    private static ItemStack lightsaberPartsBuilder(String name, Material m) {
        ItemStack i = new ItemStack(m);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        meta.addEnchant(Enchantment.CHANNELING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);
        lightsaberPartCustomItem.setPersistentData(i);
        return i;
    }
    private static void setCustomMelee() {
        GADERFFII = setCustomHelper("§4Gaderffii", Material.WOODEN_SWORD);
        GAMMOREAN_AXE = setCustomHelper("§4Gammorean Axe", Material.WOODEN_AXE, 0, -3);
    }
    private static ItemStack setCustomHelper(String name, Material m) {
        ItemStack i = new ItemStack(m);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        meta.addEnchant(Enchantment.CHANNELING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);
        simpleCustomItem.setPersistentData(i);
        return i;
    }
    private static ItemStack setCustomHelper(String name, Material m, double damage, double attack_speed) {
        return setCustomHelper(name, m, damage, attack_speed, -1);
    }
    private static ItemStack setCustomHelper(String name, Material m, double damage, double attack_speed, int custom_model) {
        ItemStack i = new ItemStack(m);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        meta.addEnchant(Enchantment.CHANNELING, 1, true);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        if (custom_model != -1) meta.setCustomModelData(custom_model);
        i.setItemMeta(meta);
        setAttributeModifier(i, Attribute.GENERIC_ATTACK_DAMAGE, damage);
        setAttributeModifier(i, Attribute.GENERIC_ATTACK_SPEED, attack_speed);
        simpleCustomItem.setPersistentData(i);
        return i;
    }
    private static void setAttributeModifier(ItemStack i, Attribute m, double amount) {
        ItemMeta meta = i.getItemMeta();
        if (m == Attribute.GENERIC_ATTACK_DAMAGE) {
            meta.addAttributeModifier(m, new AttributeModifier(UUID.randomUUID(), "",
                    amount-WeaponDamage.get(i.getType()), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        } else if (m == Attribute.GENERIC_ATTACK_SPEED) {
            meta.addAttributeModifier(m, new AttributeModifier(UUID.randomUUID(), "",
                    amount-WeaponAttackSpeed.get(i.getType()), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        } else if (m == Attribute.GENERIC_MOVEMENT_SPEED) {
            meta.addAttributeModifier(m, new AttributeModifier(UUID.randomUUID(), "",
                    amount-1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        } else {
            throw new IllegalStateException("UH OH I HAVENT MADE THAT YET");
        }
        i.setItemMeta(meta);
    }
    private static HashMap<Material, Integer> WeaponDamage = new HashMap<Material, Integer>(){{
        put(Material.WOODEN_SWORD, 4);
        put(Material.GOLDEN_SWORD, 4);
        put(Material.STONE_SWORD, 5);
        put(Material.IRON_SWORD, 6);
        put(Material.DIAMOND_SWORD, 7);
        put(Material.NETHERITE_SWORD, 8);

        put(Material.WOODEN_AXE, 7);
        put(Material.GOLDEN_AXE, 7);
        put(Material.STONE_AXE, 9);
        put(Material.IRON_AXE, 9);
        put(Material.DIAMOND_AXE, 9);
        put(Material.NETHERITE_AXE, 10);
    }};
    private static HashMap<Material, Double> WeaponAttackSpeed = new HashMap<Material, Double>(){{
        put(Material.WOODEN_SWORD, 1.6);
        put(Material.GOLDEN_SWORD, 1.6);
        put(Material.STONE_SWORD, 1.6);
        put(Material.IRON_SWORD, 1.6);
        put(Material.DIAMOND_SWORD, 1.6);
        put(Material.NETHERITE_SWORD, 1.6);

        put(Material.WOODEN_AXE, 0.8);
        put(Material.GOLDEN_AXE, 1.0);
        put(Material.STONE_AXE, 0.8);
        put(Material.IRON_AXE, 0.9);
        put(Material.DIAMOND_AXE, 1.0);
        put(Material.NETHERITE_AXE, 1.0);
    }};
}
