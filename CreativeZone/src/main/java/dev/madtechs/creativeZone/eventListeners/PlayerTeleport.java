package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import dev.madtechs.creativeZone.CreativeZone;
import dev.madtechs.creativeZone.commands.Helper;

public class PlayerTeleport implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        System.out.println("Player Teleporting!");
        var control = CreativeZone.getControl();

        Player player = event.getPlayer();

        String sourceWorld = player.getWorld().getName();
        String destinationWorld = event.getTo().getWorld().getName();

        // If the player is moving within the world they are in
        if (sourceWorld.equals(destinationWorld))
            return;

        boolean fromZone = sourceWorld.contains("c_zone");
        boolean toZone = destinationWorld.contains("c_zone");


        System.out.println(event.getCause());

        // Unknown is used to cover the end portal
        if (event.getCause() == TeleportCause.UNKNOWN && fromZone) {
            event.setCancelled(true);

            String owner = Helper.getOwnerUUID(sourceWorld);
            var location = event.getTo();

            Helper.teleportToZone(player, owner, "", location);
        }

        // If the player is moving between zone dimensions, they stay in creative and
        // nothing is swapped
        if (fromZone && toZone)
            return;

        // If the player is teleporting from a creative zone to another world
        if (fromZone) {
            control.saveCreative(player);
            control.loadSurvival(player);
            GameMode gameMode = control.getPlayerData(player).getPreviousGameMode();
            player.setGameMode(gameMode);
        }
        // If the player is going to a creative zone
        else if (toZone) {
            control.saveSurvival(player);
            control.loadCreative(player);
            player.setGameMode(GameMode.CREATIVE);
        }
    }
}
