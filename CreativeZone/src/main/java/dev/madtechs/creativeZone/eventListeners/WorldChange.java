package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import dev.madtechs.creativeZone.CreativeZone;

public class WorldChange implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityTeleport(EntityTeleportEvent event) {
        Entity entity = event.getEntity();

        String entityWorld = entity.getWorld().getName();
        String destinationWorld = event.getTo().getWorld().getName();

        // If the player is moving within the world they are in
        if (entityWorld.equals(destinationWorld)) return;


        event.setCancelled(true);
        
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        var control = CreativeZone.getControl();

        Player player = event.getPlayer();

        String playerWorld = player.getWorld().getName();
        String destinationWorld = event.getTo().getWorld().getName();

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
