package me.joshiepillow.starwars;

import me.joshiepillow.starwars.classes.Product;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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
    Server server = getServer();

    /**
     * Connects listener and gets saved data
     */
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        BountyHunter.SetAll(Data.loadData("plugins/Starwars/hunters.ser").hunters);
        Bukkit.getLogger().info("Successfully loaded Starwars version" + this.getDescription().getVersion());

    }

    /**
     * Saves data to file
     */
    @Override
    public void onDisable() {
        Data d = new Data(BountyHunter.getAll());
        d.saveData("plugins/Starwars/hunters.ser");
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
                if (h.setBountyName(args[1])) sender.sendMessage("Success");
                else sender.sendMessage("That name is taken.");
                return true;
            }
            case "hunter.list": {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < BountyHunter.getAll().size(); i++) {
                    h = BountyHunter.getAll().get(i);
                    s.append(h.toString());
                    s.append("\n");
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
                    sender.sendMessage("&7Current Balance --&r " + h.getBal() + "&6C&r");
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
            /*case "hunter.start": {
                if (!(sender instanceof Player)) return false;
                sender.sendMessage("What is your name?");
            }*/
            case "product.create": {
                if (args.length != 3) return false;
                p = Product.create(args[0], Integer.parseInt(args[1]), args[2]);
                if (p == null) sender.sendMessage("That name is already taken.");
                else sender.sendMessage("Success!\n" + p.toString());
                return true;
            }
            case "product.cmd": {
                if (args.length > 2 || args.length == 0) return false;
                if (args.length == 1) {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    sender.sendMessage(p.getCommand());
                }
                if (args.length == 2) {
                    p = Product.getByName(args[0]);
                    if (p == null) {
                        sender.sendMessage("Product does not exist.");
                        return true;
                    }
                    p.setCommand(args[1]);
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
            case "test": {
                ((Player) sender).openInventory(Inventories.myInventory);
                return true;
            }
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
     * @param y2 Z-coord of second påoint
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