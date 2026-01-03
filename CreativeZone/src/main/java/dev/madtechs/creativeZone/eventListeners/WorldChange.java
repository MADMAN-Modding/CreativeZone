package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldChange implements Listener {
@EventHandler (priority = EventPriority.LOWEST)
public void onPlayerTeleport(PlayerTeleportEvent event) {
    Player player = event.getPlayer();
    if (player.getWorld().getName().contains("c_zone")) {

        var destEnvironment = event.getTo().getWorld().getEnvironment();
        // Check if the player's destination is in the Nether dimension
        if (destEnvironment == World.Environment.NETHER || destEnvironment == World.Environment.THE_END) {
            player.sendMessage("You cannot teleport to the Nether or End while in a creative zone.");
            event.setCancelled(true); // Cancel the teleport event
        }
    }
}
}
