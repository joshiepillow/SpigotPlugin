package me.joshiepillow.starwars;

import me.joshiepillow.starwars.classes.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MyListener implements Listener
{
    private JavaPlugin plugin;
    MyListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.dispatchCommand(event.getPlayer(), "start"), 1L);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        Inventories.onInventoryClick(event);
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack i = event.getInventory().getItem(0);
        if (i != null && i.getType().equals(Material.PLAYER_HEAD)) {
            event.setResult(null);
            for (HumanEntity datum : event.getViewers()) {
                ((Player) datum).updateInventory();
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().equals(Material.PLAYER_HEAD) ||
            event.getBlockPlaced().getType().equals(Material.PLAYER_WALL_HEAD))
            event.setCancelled(true);
    }

    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {

    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            List<ItemStack> i_list = event.getDrops();
            ItemStack i = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta m = (SkullMeta) i.getItemMeta();
            m.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_" + event.getEntity().getName().replace(" ","")));
            m.setDisplayName(event.getEntity().getName());
            i.setItemMeta(m);
            i_list.add(i);
        }
    }
    /*@EventHandler
    public void onSprint(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 10));
    }*/
    /*@EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            p.sendMessage("A");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> p.setVelocity(new Vector(0,0,0)), 1L);

        }
    }*/

}
