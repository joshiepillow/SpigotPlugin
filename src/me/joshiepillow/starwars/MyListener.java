package me.joshiepillow.starwars;

import me.joshiepillow.starwars.classes.BountyHunter;
import me.joshiepillow.starwars.classes.CustomItem;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MyListener implements Listener
{
    static ItemStack[] helmets = {
            new ItemStack(Material.LEATHER_HELMET),
            new ItemStack(Material.CHAINMAIL_HELMET),
            new ItemStack(Material.IRON_HELMET),
            new ItemStack(Material.GOLDEN_HELMET),
            new ItemStack(Material.DIAMOND_HELMET),
            new ItemStack(Material.NETHERITE_HELMET),
    };

    private JavaPlugin plugin;
    MyListener(JavaPlugin plugin) {
        this.plugin = plugin;
        /*Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()->{
            //System.out.println(Bukkit.getPlayer("Joshiepillow").getVelocity().length());
            for (int i = 0; i < Bukkit.getWorlds().size(); i++){
                List<LivingEntity> l = Bukkit.getWorlds().get(i).getLivingEntities();
                l.forEach((creature -> {
                    if (creature instanceof Creature) {
                        Double d = 0.01;
                        if (!creature.getVelocity().isInSphere(new Vector(0,0,0), d)) {
                            creature.getEquipment().setHelmet(helmets[1]);
                        } else {
                            creature.getEquipment().setHelmet(helmets[0]);
                        }
                    }
                }));
            }
        }, 0, 1);*/
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        event.getPlayer().getInventory().addItem(CustomItem.FORCE_ABILITIES_HOLDER);
        // books are :pogchamp:
    	ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    	List<String> booktext = new ArrayList<String>(){{
    	    add("&7Welcome to &r&e&lGalaxies Horizon&r&7!\n" +
                    "This is a test\n" +
                    " \n" +
                    "Please enter text here\n" +
                    "Found in MyListener.java\n" +
                    "In MyListener.onPlayerrJoin\n" +
                    "At line 44\n" +
                    "Fill with text\n" +
                    "It no work-> contact me\n");
        }};


    	//NO CURSING IN THE COMMENTS
        assert book.getItemMeta() != null;
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setTitle("You've been caught hacking");
        bookMeta.setAuthor("the Devs");
        bookMeta.setPages(booktext);
        book.setItemMeta(bookMeta);
    	event.getPlayer().openBook(book);

    	if(!event.getPlayer().hasPlayedBefore()) {
            // give non rank
            UserManager manager = LuckPermsProvider.get().getUserManager();
            final User user = manager.getUser(event.getPlayer().getUniqueId());
            assert user != null;
            user.setPrimaryGroup("non");
        }
    	if (BountyHunter.getByUUID(event.getPlayer().getUniqueId())==null)
            BountyHunter.create(event.getPlayer().getUniqueId(), "");


    	// also send a message for players
    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> Bukkit.dispatchCommand(event.getPlayer(), "start"), 1L);
    	// and we are done!
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        /*Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (event.getCurrentItem() != null &&
            event.getCurrentItem().getItemMeta() != null) {
            if (event.getCurrentItem().getItemMeta().getPersistentDataContainer()
                    .has(Inventories.getKey(), PersistentDataType.BYTE_ARRAY)) {
                Inventories.onInventoryClick(event);
            //} else if (event.getCurrentItem().getItemMeta().getPersistentDataContainer()
            //        .has(Force.getKey(), PersistentDataType.BYTE_ARRAY)) {
            //    Force.onInventoryClick(event);
            } else {

            }
        }*/
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

    /*@EventHandler
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
    }*/

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
            event.getDrops().removeIf(itemStack ->
                    itemStack.getItemMeta() != null &&
                    itemStack.getItemMeta().getLore() != null &&
                    itemStack.getItemMeta().getLore().get(0).equals("Lightsaber")
            );

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        BountyHunter b = BountyHunter.getByUUID(event.getPlayer().getUniqueId());
        if (b != null && b.Lightsaber != null)
            event.getPlayer().getInventory().addItem(b.Lightsaber);
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
    @EventHandler
    public void onCraftEvent(CraftItemEvent event) {
        System.out.println("Z");
        if (event.getRecipe().getResult().getItemMeta() != null &&
            event.getRecipe().getResult().getItemMeta().getDisplayName().contains("Lightsaber")) {
            System.out.println("A");
            BountyHunter b = BountyHunter.getByUUID(event.getWhoClicked().getUniqueId());
            if (b != null && b.Lightsaber != null) {
                System.out.println("B");
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage("You can't craft another lightsaber");
            } else if (b != null){
                System.out.println("C");
                b.Lightsaber = event.getRecipe().getResult();
            }
        }
    }

    /*@EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof  Creature) {
            event.getEntity().addPotionEffect(
                    new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE,
                    1, false, false));
            event.getEntity().getEquipment().setHelmet(helmets[0]);
        }
    }*/

    @EventHandler
    public void onFallDamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL &&
            CustomItem.ForceLand.contains(event.getEntity().getUniqueId()))
            event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CRIMSON_BUTTON) {

        }
    }

}
