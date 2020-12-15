package me.joshiepillow.starwars;

import me.joshiepillow.starwars.classes.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public class MemeListener implements Listener
{
    private JavaPlugin plugin;
    MemeListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void place(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        p.setHealth(15);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity p = (LivingEntity) event.getEntity();
            if (p.getEquipment() != null &&
                p.getEquipment().getChestplate() != null &&
                p.getEquipment().getChestplate().getType()==Material.NETHERITE_CHESTPLATE) {
                double newHp = p.getHealth() - event.getDamage();
                if (newHp < 0) {
                    //let entity die
                } else {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                            () -> p.setHealth(newHp), 1L);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            LivingEntity p = (LivingEntity) event.getDamager();
            if (p.getEquipment() != null &&
                p.getEquipment().getItemInMainHand().getType()==Material.FISHING_ROD) {

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
                        () -> {
                            event.getEntity().setVelocity(event.getEntity().getVelocity().multiply(-10));
                            System.out.println(event.getEntity().getVelocity().toString());
                        }, 1L);
            }
        }
    }
    @EventHandler
    public void OnPlayerFishEvent(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            Player p = event.getPlayer();
            Entity e = event.getCaught();
            Vector v = p.getLocation().subtract(e.getLocation()).toVector().multiply(0.3);
            v.setY(0.3 * v.getY());
            e.setVelocity(v);
        }
    }

}
