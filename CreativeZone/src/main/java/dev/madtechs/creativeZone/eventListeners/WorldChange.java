package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import dev.madtechs.creativeZone.CreativeZone;

public class WorldChange implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        String playerWorld = player.getWorld().getName();
        String destinationWorld = event.getTo().getWorld().getName();

        // If the player is moving within the world they are in
        if (playerWorld.equals(destinationWorld)) return;

        var control = CreativeZone.getControl();

        // If the player is teleporting from a creative zone to another world
        if (playerWorld.contains("c_zone") && !destinationWorld.contains("c_zone")) {
            control.saveCreative(player);
            control.loadSurvival(player);
            
            GameMode gameMode = control.getPlayerData(player).getPreviousGameMode();
            player.setGameMode(gameMode);
        }
        // If the player is going to a creative zone
        else if (destinationWorld.contains("c_zone")) {
            control.saveSurvival(player);
            control.loadCreative(player);
            player.setGameMode(GameMode.CREATIVE);
        }
    }
}
