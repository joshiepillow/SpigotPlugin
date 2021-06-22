package me.joshiepillow.starwars;

import de.leonhard.storage.Yaml;
import net.luckperms.api.LuckPerms;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

/**
 * Main plugin class
 * @author Joshiepillow
 */

public class Main extends JavaPlugin {
    /**
     * Server object
     */
    private Server server = getServer();
    private Yaml playerFile = new Yaml("PlayerData", "plugins/Starwars/");
    private PlayerData playerData;

    public LuckPerms api;
    @Override
    public void onEnable() {
        //pass over save file
        playerData = new PlayerData(playerFile);

    	// start luckperms API
    	RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    	if (provider != null) {
    	    api = provider.getProvider();
    	}

        getServer().getPluginManager().registerEvents(new MyListener(), this);

        Bukkit.getLogger().info("Successfully loaded Starwars version" + this.getDescription().getVersion());
        CustomItem.setItems(this);
    }

    /**
     * Saves data to file
     */
    @Override
    public void onDisable() {
        playerData.saveAll();
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
        switch (command.getName().toLowerCase()) {
            case "forcepowers.grant": {
                if (args.length != 2) return false;
                Player p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage("No player found.");
                    return true;
                }
                if (args[1].equals("*")) {
                    IndividualPlayerData ipd = playerData.getDataByUUID(p.getUniqueId());
                    for (String s : CustomItem.forcePowerList) {
                        ipd.setUnlockedForcePowers(s, true);
                    }
                    sender.sendMessage("Success!");
                    return true;
                }
                int x;
                try {
                    x = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    sender.sendMessage("Second argument must be int.");
                    return true;
                }
                if (x < CustomItem.forcePowerList.size()) {
                    IndividualPlayerData ipd = playerData.getDataByUUID(p.getUniqueId());
                    ipd.setUnlockedForcePowers(CustomItem.forcePowerList.get(x), true);
                    sender.sendMessage("Success!");
                    return true;
                }
            }
            case "forcepowers.take": {
                if (args.length != 2) return false;
                Player p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage("No player found.");
                    return true;
                }
                if (args[1].equals("*")) {
                    IndividualPlayerData ipd = playerData.getDataByUUID(p.getUniqueId());
                    for (String s : CustomItem.forcePowerList) {
                        ipd.setUnlockedForcePowers(s, false);
                    }
                    sender.sendMessage("Success!");
                    return true;
                }
                int x;
                try {
                    x = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    sender.sendMessage("Second argument must be int.");
                    return true;
                }
                if (x < CustomItem.forcePowerList.size()) {
                    IndividualPlayerData ipd = playerData.getDataByUUID(p.getUniqueId());
                    ipd.setUnlockedForcePowers(CustomItem.forcePowerList.get(x), false);
                    sender.sendMessage("Success!");
                    return true;
                }
            }
            case "forcepowers.item": {
                ((Player)sender).getInventory().addItem(CustomItem.FORCE_ABILITIES_HOLDER);
            }
            case "forcepowers.save": {
                playerData.saveAll();
            }

            default:
                sender.sendMessage("This shouldn't be possible...Must be a bug.");
        }
        return false;
    }

    public PlayerData getPlayerData() {
        return playerData;
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