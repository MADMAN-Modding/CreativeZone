package dev.madtechs.creativeZone.dataControl;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Control {
    private HashMap<Player, PlayerData> playerData;

    public Control() {
        playerData = new HashMap<Player, PlayerData>();
    }

    public void addPlayer(Player player, PlayerData data) {
        playerData.put(player, data);
    }

    public PlayerData getPlayerData(Player player) {
        if (playerData.containsKey(player)) {
            return playerData.get(player);
        } else {
            PlayerData data = new PlayerData(player.getUniqueId(), new ArrayList<>(), player.getWorld().getName().toString(), player.getGameMode());

            playerData.put(player, data);

            return data;
        }
    }

    public void setPreviousGameMode(Player player) {
        if (playerData.containsKey(player)) {
            playerData.get(player).setPreviousGameMode(player.getGameMode());
        } else {
            PlayerData data = new PlayerData(player.getUniqueId(), new ArrayList<>(), player.getWorld().getName().toString(), player.getGameMode());

            playerData.put(player, data);
        }
    }
}
