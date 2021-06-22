package me.joshiepillow.starwars;

import de.leonhard.storage.Yaml;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
    private Yaml dataFile;
    private HashMap<UUID, IndividualPlayerData> players = new HashMap<>();
    public PlayerData(Yaml dataFile) {
        this.dataFile = dataFile;
        this.dataFile.setDefault("players", new HashMap<UUID, IndividualPlayerData>());
    }
    public void onPlayerJoinEvent(Player p) {
        IndividualPlayerData ipd = players.getOrDefault(p.getUniqueId(),
                dataFile.getOrSetDefault("players."+p.getUniqueId(), new IndividualPlayerData(p.getDisplayName())));
        players.put(p.getUniqueId(), ipd);
    }
    public IndividualPlayerData getDataByUUID(UUID uuid) {
        return players.getOrDefault(uuid, null);
    }
    public void saveAll() {
        dataFile.set("players", players);
    }
}

class IndividualPlayerData {
    private String playerName;
    private HashMap<String, Boolean> unlockedForce;
    private Integer cooldowns;

    IndividualPlayerData(String playerName, HashMap<String, Boolean> unlockedForce, Integer cooldowns) {
        this.playerName = playerName;
        this.unlockedForce = unlockedForce;
        this.cooldowns = cooldowns;
    }
    IndividualPlayerData(String playerName) {
        this.playerName = playerName;
        unlockedForce = new HashMap<>();
        cooldowns = 0;
        for (String s : CustomItem.forcePowerList) {
            unlockedForce.put(s, false);
        }
    }
    public Boolean getUnlockedForcePowers(String s) {
        return unlockedForce.get(s);
    }
    public Boolean setUnlockedForcePowers(String s, Boolean b) {
        return unlockedForce.put(s, b);
    }
    public Integer getCooldowns() {
        return cooldowns;
    }
    public Integer setCooldowns(Integer i) {
        int old = cooldowns;
        cooldowns = i;
        return old;
    }
    public String getPlayerName() {
        return playerName;
    }
}