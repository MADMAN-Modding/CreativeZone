package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.madtechs.creativeZone.CreativeZone;
import dev.madtechs.creativeZone.dataControl.Control;

public class PlayerJoinLeave implements Listener {
    Control control = CreativeZone.getControl();


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        var gameMode = player.getGameMode();

        // If the player joins in creative
        if (gameMode == GameMode.CREATIVE) {
            var control = CreativeZone.getControl();

            // If the player is in a zone, load their creative inventory
            if (player.getWorld().getName().contains("c_zone"))
                control.loadCreative(player);
            // Otherwise, load their previous gameMode and
            else {
                var prevGameMode = control.getPlayerData(player).getPreviousGameMode();

                player.setGameMode(prevGameMode);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLeave(PlayerQuitEvent event) {
        var player = event.getPlayer();

        if (player.getWorld().getName().contains("c_zone")) {
            control.saveCreative(player);
        }
    }
}