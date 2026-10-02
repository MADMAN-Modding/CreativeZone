package dev.madtechs.creativeZone.eventListeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import dev.madtechs.creativeZone.commands.Helper;
import dev.madtechs.creativeZone.voidWorld.VoidWorld;

public class ZonePortal implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        String playerWorld = player.getWorld().getName();

        // Return if not in a zone, real world portals stay vanilla
        if (!playerWorld.contains("c_zones/")) {
            return;
        }

        Location to = event.getTo();

        // The real world the vanilla destination resolved to
        World realDestination = to.getWorld();

        // Pull the uuid out of the zone name
        String uuid = Helper.getOwnerUUID(playerWorld);

        String zoneName = "c_zones/" + uuid + VoidWorld.getSuffix(realDestination);

        World zone = Bukkit.getWorld(zoneName);

        // Create the destination zone if it doesn't exist yet
        if (zone == null) {
            zone = VoidWorld.getVoidWorld(uuid, realDestination).createWorld();
        }

        to.setWorld(zone);

        event.setTo(to);
    }
}