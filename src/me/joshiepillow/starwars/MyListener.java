package me.joshiepillow.starwars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MyListener implements Listener
{
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        JavaPlugin.getPlugin(Main.class).getPlayerData().onPlayerJoinEvent(event.getPlayer());
        if (!event.getPlayer().hasPlayedBefore())
            event.getPlayer().getInventory().addItem(CustomItem.FORCE_ABILITIES_HOLDER);
    }

    @EventHandler
    public void onFallDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL &&
            CustomItem.ForceLand.contains(event.getEntity().getUniqueId()))
            event.setCancelled(true);
    }

}
