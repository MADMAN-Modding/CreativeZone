package dev.madtechs.creativeZone.dataControl;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerData {
    private UUID playerUUID;
    private ArrayList<UUID> allowedPlayers;
    private String previousWorld;
    private GameMode previousGameMode;

    public PlayerData(UUID playerUUID, ArrayList<UUID> allowedPlayers, String previousWorld, GameMode previousGameMode) {
        this.playerUUID = playerUUID;
        this.allowedPlayers = allowedPlayers;
        this.previousWorld = previousWorld;
        this.previousGameMode = previousGameMode;
    }

    public void setPreviousWorld(String previousWorld) {
        this.previousWorld = previousWorld;
    }

    public void setPreviousGameMode(GameMode previousGameMode) {
        this.previousGameMode = previousGameMode;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public ArrayList<UUID> getAllowedPlayers() {
        return allowedPlayers;
    }

    public String getPreviousWorld() {
        return previousWorld;
    }

    public GameMode getPreviousGameMode() {
        return previousGameMode;
    }


    /**
     * Checks if the player is allowed in the zone
     * @param player
     * @return If the player is allowed
     */
    public boolean isPlayerAllowed(Player player) {
        return isPlayerAllowed(player.getUniqueId());
    }
    /**
     * Checks if the player is allowed in the zone
     * @param player
     * @return If the player is allowed
     */
    public boolean isPlayerAllowed(UUID player) {
        for (UUID uuid : allowedPlayers) {
            if (uuid.equals(player)) {
                return true;
            }    
        }

        return false;
    }

    public void allowPlayer(String stringPlayer) {
        Player player = Bukkit.getPlayer(stringPlayer);

        allowedPlayers.add(player.getUniqueId());
    }
}
