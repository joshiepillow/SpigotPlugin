package me.joshiepillow.starwars;

import me.joshiepillow.starwars.classes.*;
import net.luckperms.api.LuckPerms;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Main plugin class
 * @author Joshiepillow
 */

public class Main extends JavaPlugin {
    /**
     * Server object
     */
    private Server server = getServer();

    /**
     * Connects listener and gets saved data
     */
    @Override
    public void onEnable() {
    	// start luckperms API
    	RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    	if (provider != null) {
    	    LuckPerms api = provider.getProvider();
    	}

        getServer().getPluginManager().registerEvents(new MyListener(this), this);
        Data d = Data.loadData("plugins/Starwars/data.ser");
        if (d != null) {
            BountyHunter.setAll(d.hunters);
        }
        Bukkit.getLogger().info("Successfully loaded Starwars version" + this.getDescription().getVersion());
        CustomItem.setItems();
        Recipes.setItems(this);
        Products.setItems();
        Inventories.init(this);
        Shop.setItems();
    }

    /**
     * Saves data to file
     */
    @Override
    public void onDisable() {
        Data d = new Data(BountyHunter.getAll());
        d.saveData("plugins/Starwars/data.ser");
    }

    /**
     * Called when a command is sent
     * @param sender Handled by Minecraft
     * @param command Handled by Minecraft
     * @param label Handled by Minecraft
     * @param args Handled by Minecraft
     * @return Handled by Minecraft
     */
    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        BountyHunter h;
        Product p;
        switch (command.getName().toLowerCase()) {
            /*case "mycommand":
                sender.sendMessage("You ran /mycommand!");
                return true;


            case "who_at":
                if (args.length > 1) {
                    sender.sendMessage("Requires 0 or 1 argument(s).");
                    return false;
                }
                World world = null;
                if (args.length == 0) {
                    Player p = (Player) sender;
                    world = p.getWorld();
                    List<Player> players = p.getWorld().getPlayers();
                    sender.sendMessage("Players in " + p.getWorld().getName() + ": " + players.toString());
                    return true;
                }
                if (args.length == 1) {
                    world = server.getWorld(args[0]);

                }
                if (world == null) {
                    sender.sendMessage("World does not exist");
                    return false;
                }
                List<Player> players = world.getPlayers();
                sender.sendMessage("Players in " + world.getName() + ": " + players.toString());
                return true;

            case "players_at":
                //make sure we have 4 args
                if (args.length != 4) {
                sender.sendMessage("Requires 4 arguments.");
                return false;
            }

                //declare four double vars
                Double x1, y1, x2, y2;
                try {
                //assign args to temporary vars
                Double tempx1 = Double.parseDouble(args[0]);
                Double tempy1 = Double.parseDouble(args[1]);
                Double tempx2 = Double.parseDouble(args[2]);
                Double tempy2 = Double.parseDouble(args[3]);

                //(x1, y1) are the smaller ones, (x2, y2) are the larger ones
                x1 = tempx1 < tempx2 ? tempx1 : tempx2;
                y1 = tempy1 < tempy2 ? tempy1 : tempy2;
                x2 = tempx1 > tempx2 ? tempx1 : tempx2;
                y2 = tempy1 > tempx2 ? tempy1 : tempy2;

            } catch (NumberFormatException e) { // make sure args are numbers
                sender.sendMessage("Arguments must be numbers.");
                return false;
            }
                sender.sendMessage(players_in(x1, y1, x2, y2)+" player(s) in area");
                return true;
            case "summon_boss":
                if (args.length != 0) {
                    sender.sendMessage("No arguments required.");
                    return false;
                }
                //server.dispatchCommand(Bukkit.getConsoleSender(), "summon iron_golem ~ ~ ~");
                Player p = (Player) sender;
                IronGolem ir = (IronGolem) p.getWorld().spawnEntity(p.getLocation(), EntityType.IRON_GOLEM);
                ir.setTarget(p);
                ir.damage(10);
                ir.setRemoveWhenFarAway(false);

                return true;*/
            case "hunter.create": {
                if (args.length == 1) {
                    if (!(sender instanceof Player)) return false;
                    if (BountyHunter.getByUsername(sender.getName()) != null) {
                        sender.sendMessage("You already have a BountyHunter");
                        return true;
                    }
                    h = BountyHunter.create(sender.getName(), args[0]);
                    sender.sendMessage("New BountyHunter created.");
                    return true;
                } else if (args.length == 2) {
                    Player player = server.getPlayer(args[0]);
                    if (player == null) sender.sendMessage("Player is not online or does not exist.");
                    else if (BountyHunter.getByUsername(player.getName()) != null)
                        sender.sendMessage("Player is already a BountyHunter.");
                    else {
                        h = BountyHunter.create(player.getName(), args[1]);
                        sender.sendMessage("New BountyHunter created.");
                    }
                    return true;
                }
                return false;
            }
            case "hunter.name": {
                if (args.length != 2) return false;
                h = BountyHunter.getByUsername(args[0]);
                if (h == null) {
                    sender.sendMessage("BountyHunter does not exist.");
                    return true;
                }
                if (h.setName(args[1])) sender.sendMessage("Success");
                else sender.sendMessage("That name is taken.");
                return true;
            }
            case "hunter.list": {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < BountyHunter.getAll().size(); i++) {
                    h = BountyHunter.getAll().get(i);
                    s.append("(").append(h.getUsername()).append(")").append(h.toString()).append("\n");
                }
                sender.sendMessage(s.toString());
                return true;
            }
            case "hunter.bal": {
                if (args.length == 1) {
                    h = BountyHunter.getByUsername(args[0]);
                    if (h == null) {
                        sender.sendMessage("BountyHunter does not exist.");
                        return true;
                    }
                    sender.sendMessage("§7Current Balance --§r " + h.getBal() + "§6C§r");
                    return true;
                }
                if (args.length == 2) {
                    h = BountyHunter.getByUsername(args[0]);
                    if (h == null) {
                        sender.sendMessage("BountyHunter does not exist.");
                        return true;
                    }
                    if (h.changeBal(Integer.parseInt(args[1])))
                        sender.sendMessage("Change successful.\nNew balance -- " + h.getBal() + "C");
                    else sender.sendMessage("Can't pay more than you have!\nCurrent Balance -- " + h.getBal() + "C");
                    return true;
                }
                return false;
            }
            case "hunter.buy": {
                if (args.length == 2) {
                    h = BountyHunter.getByUsername(args[0]);
                    p = Product.getByName(args[1]);
                    if (h!=null && p!=null) {
                        List<String> s = h.buy(p);
                        if (s!=null) {
                            sender.sendMessage("Success!");
                            for (String datum : s)
                                if (!datum.isEmpty())
                                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), datum);
                        } else {
                            sender.sendMessage("You do not have the money to do that.");
                        }
                    } else {
                        sender.sendMessage("BountyHunter or Product does not exist.");
                    }
                    return true;
                }
                return false;
            }
            case "hunter.quest": {
                if (args.length <  4) return false;
                h = BountyHunter.getByUsername(args[0]);
                if (h != null) {
                    StringBuilder s = new StringBuilder();
                    for(int i = 3; i < args.length; i++) {
                        s.append(args[i]);
                        s.append(" ");
                    }
                    h.addQuest("mob", args[1], Integer.parseInt(args[2]), s.toString());
                    sender.sendMessage("Success!");
                } else sender.sendMessage("BountyHunter does not exist.");
                return true;
            }
            case "hunter.submit": {
                if (args.length == 1) {
                    h = BountyHunter.getByUsername(args[0]);
                    Player player = Bukkit.getPlayer(args[0]);
                    if (h != null && player != null) {
                        List<String> s = h.submit(player.getInventory());
                        for (String datum : s) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), datum);
                        }
                        sender.sendMessage("Success!");
                    } else sender.sendMessage("Player or BountyHunter does not exist.");
                    return true;
                } else if (args.length == 2) {
                    h = BountyHunter.getByUsername(args[0]);
                    Player player = Bukkit.getPlayer(args[0]);
                    if (h != null && player != null) {
                        List<String> s = h.submit(player.getInventory(), Integer.parseInt(args[1]));
                        for (String datum : s) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), datum);
                        }
                    } else sender.sendMessage("Player or BountyHunter does not exist.");
                    return true;
                }
            }
            case "hunter.quests": {
                if (args.length != 1) return false;
                h = BountyHunter.getByUsername(args[0]);
                if (h!=null) {
                    System.out.println("Checkpoint A");
                    if (sender instanceof Player) {
                        System.out.println("Checkpoint B");
                        ((Player) sender).openInventory(h.QuestGui());
                    } else {
                        sender.sendMessage(h.quests());
                    }
                }
                else sender.sendMessage("BountyHunter does not exist.");
                return true;
            }
            case "hunter.erase": {
                if (args.length != 1) return false;
                h = BountyHunter.getByUsername(args[0]);
                if (h == null) sender.sendMessage("Product does not exist.");
                else {
                    h.erase();
                    sender.sendMessage("Success!");
                }
                return true;
            }

            /*case "product.create": {
                if (args.length < 3) return false;
                StringBuilder s = new StringBuilder();
                for(int i = 2; i < args.length; i++) {
                    s.append(args[i]);
                    s.append(" ");
                }
                p = Product.create(args[0], Integer.parseInt(args[1]), s.toString());
                if (p == null) sender.sendMessage("That name is already taken.");
                else sender.sendMessage("Success!\n" + p.toString());
                return true;
            }
            case "product.cmd": {
                if (args.length == 0) return false;
                if (args.length == 1) {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    sender.sendMessage(p.getCommand());
                }
                else {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    StringBuilder s = new StringBuilder();
                    for(int i = 1; i < args.length; i++) {
                        s.append(args[i]);
                        s.append(" ");
                    }
                    p.setCommand(s.toString());
                    sender.sendMessage("Success!\nNew command: " + p.getCommand());
                }
                return true;
            }
            case "product.name": {
                if (args.length > 2 || args.length == 0) return false;
                if (args.length == 1) {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    sender.sendMessage(p.getName());
                }
                if (args.length == 2) {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    if (p.setName(args[1]))
                        sender.sendMessage("Success!\nNew name: " + p.getName());
                    else sender.sendMessage("That name is already taken.");
                }
                return true;
            }
            case "product.cost": {
                if (args.length > 2 || args.length == 0) return false;
                if (args.length == 1) {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    sender.sendMessage("" + p.getCost());
                }
                if (args.length == 2) {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    p.setCost(Integer.parseInt(args[1]));
                    sender.sendMessage("Success!\nNew cost: " + p.getCost());
                }
                return true;
            }
            case "product.list": {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < Product.getAll().size(); i++) {
                    p = Product.getAll().get(i);
                    s.append(p.toString());
                    s.append("\n");
                }
                sender.sendMessage(s.toString());
                return true;
            }
            case "product.erase": {
                if (args.length != 1) return false;
                p = Product.getByName(args[0]);
                if (p == null) sender.sendMessage("Product does not exist.");
                else {
                    p.erase();
                    sender.sendMessage("Success!");
                }
                return true;
            }*/

            case "test": {
                Player player = (Player) sender;
                ItemMeta m = (player).getInventory().getItemInMainHand().getItemMeta();
                if (m != null && m.getDisplayName().equals("Pig Head"))
                    player.openInventory(Shop.MAIN.getInventory());
                else {
                    if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        player.getInventory().setItemInMainHand(new ItemStack(Material.CARROT_ON_A_STICK));
                    } else if (player.getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
                        player.getInventory().setItemInOffHand(new ItemStack(Material.CARROT_ON_A_STICK));
                    } else {
                        sender.sendMessage("Must have open slot!");
                        return true;
                    }
                    Pig pig = player.getWorld().spawn(player.getLocation(), Pig.class);
                    pig.addPotionEffects(new ArrayList<PotionEffect>() {{
                        add(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE,
                            1, false, false));
                        add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE,
                            255, false, false));
                        add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE,
                            15, false, false));
                    }});
                    pig.addPassenger(player);

                }
                return true;
            }
            case "shop.open": {
                if (args.length != 2) return false;
                else {
                    Player player = server.getPlayer(args[0]);
                    Inventories inv = Inventories.getByName(args[1]);
                    if (player==null) sender.sendMessage("That player does not exist or is offline.");
                    else if (inv==null) sender.sendMessage("That page does not exist.");
                    else {
                        player.openInventory(inv.getInventory());
                        sender.sendMessage("Success!");
                    }
                    return true;
                }
            }
            case "shop.close": {
                if (args.length != 1) return false;
                else {
                    Player player = server.getPlayer(args[0]);
                    if (player==null) sender.sendMessage("That player does not exist or is offline.");
                    else {
                        player.closeInventory();
                        sender.sendMessage("Success!");
                    }
                    return true;
                }
            }
            case "shop.buy": {
                if (args.length != 2) return false;
                else {
                    h = BountyHunter.getByUsername(args[0]);
                    p = Product.getByName(args[1]);
                    if (h != null && p != null) {
                        List<String> s = h.buy(p);

                        if (s != null) {
                            for (String datum : s) {
                                Bukkit.dispatchCommand(server.getConsoleSender(), datum);
                            }
                            Bukkit.dispatchCommand(server.getConsoleSender(), "shop.close " + args[0]);
                            sender.sendMessage("Success!");
                        } else sender.sendMessage("You do not have the money to do that.");

                    } else sender.sendMessage("BountyHunter or Product does not exist.");
                }
                return true;
            }

            case "start":
                sender.sendMessage("§8Hey, " + sender.getName() + ". Welcome to §r§e§lGalaxiesHorizon!\n" +
                        "§r§8GalaxiesHorizon is a Star Wars themed multiplayer map with custom weapons, quests, guns and more!\n" +
                        " \n" +
                        "§r§eTo get started type /name <your nickname>\n" +
                        "List of commands:\n" +
                        "     /nick <nick>  -- name yourself\n" +
                        "     /shop         -- open shop\n" +
                        "     /me           -- lists your info\n" +
                        "     /start        -- show this message again.");
                return true;
            case "nick":
                if (sender instanceof Player) {
                    if (args.length < 1) sender.sendMessage("Please choose a name.");
                    else if (args.length > 1) sender.sendMessage("Name must be one word.");
                    else {
                        if (BountyHunter.getByUsername(args[0]) != null)
                            sender.sendMessage("You already chose your name!");
                        else if (BountyHunter.create(sender.getName(), args[0]) == null)
                            sender.sendMessage("That name is already taken!");
                        else sender.sendMessage("Success!");
                        return true;
                    }
                    return false;
                } else sender.sendMessage("This command can only be run by a player.");
                return true;
            case "shop":
                if (sender instanceof Player) {
                    if (BountyHunter.getByUsername(sender.getName()) == null)
                        sender.sendMessage("You need to choose a name first!");
                    else ((Player) sender).openInventory(Shop.MAIN.getInventory());
                } else sender.sendMessage("This command can only be run by a player.");
                return true;
            case "me":
                if (sender instanceof Player) {
                    h = BountyHunter.getByUsername(sender.getName());
                    if (h==null) sender.sendMessage("You need to choose a name first!");
                    else sender.sendMessage(h.toString());
                } else sender.sendMessage("This command can only be run by a player.");
                return true;
            case "list":
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < BountyHunter.getAll().size(); i++) {
                    h = BountyHunter.getAll().get(i);
                    s.append(h.toString());
                    s.append("\n");
                }
                sender.sendMessage(s.toString());
                return true;

            default:
                sender.sendMessage("This shouldn't be possible...Must be a bug.");
        }
        return false;
    }

    /**
     * Counts the number of players between two points
     * @param x1 X-coord of first point
     * @param y1 Z-coord of first point
     * @param x2 X-coord of second point
     * @param y2 Z-coord of second point
     * @return number of players
     */
    private int players_in(Double x1, Double y1, Double x2, Double y2) {
        //gets all online players
        Collection<? extends Player> onlinePlayers = server.getOnlinePlayers();

        //count number of players online in area (don't ask me how this works I have no clue)
        return (int) onlinePlayers.stream().map(Entity::getLocation).filter(
                (l) ->
                        l.getX() >= x1 &&
                                l.getX() <= x2 &&
                                l.getY() >= y1 &&
                                l.getY() <= y2)
                .count();
    }
}