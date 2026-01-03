package dev.madtechs.creativeZone.dataControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import dev.madtechs.creativeZone.CreativeZone;

public class Control {

    private final HashMap<UUID, PlayerData> playerData = new HashMap<>();

    /* ===================== */
    /*   DATA ACCESS         */
    /* ===================== */

    public PlayerData getPlayerData(Player player) {
        return playerData.computeIfAbsent(
            player.getUniqueId(),
            uuid -> new PlayerData(
                uuid,
                new ArrayList<>(),
                player.getWorld().getName(),
                player.getGameMode()
            )
        );
    }

    public void addPlayer(Player player, PlayerData data) {
        playerData.put(player.getUniqueId(), data);
    }

    /* ===================== */
    /*   STATE TRACKING      */
    /* ===================== */

    public void setPreviousGameMode(Player player) {
        getPlayerData(player).setPreviousGameMode(player.getGameMode());
    }

    public void setPreviousWorld(Player player) {
        getPlayerData(player).setPreviousWorld(player.getWorld().getName());
    }

    /* ===================== */
    /*   INVENTORY + XP      */
    /* ===================== */

    public void saveSurvival(Player player) {
        getPlayerData(player).saveSurvival(player);
    }

    public void saveCreative(Player player) {
        getPlayerData(player).saveCreative(player);
    }

    public void loadSurvival(Player player) {
        Bukkit.getScheduler().runTask(
            CreativeZone.getInstance(),
            () -> getPlayerData(player).loadSurvival(player)
        );
    }

    public void loadCreative(Player player) {
        Bukkit.getScheduler().runTask(
            CreativeZone.getInstance(),
            () -> getPlayerData(player).loadCreative(player)
        );
    }

    /* ===================== */
    /*   CLEANUP (OPTIONAL)  */
    /* ===================== */

    public void removePlayer(Player player) {
        playerData.remove(player.getUniqueId());
    }
}
