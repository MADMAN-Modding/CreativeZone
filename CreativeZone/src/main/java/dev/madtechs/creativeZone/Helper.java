package dev.madtechs.creativeZone;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dev.madtechs.creativeZone.voidWorld.VoidWorld;
import dev.madtechs.creativeZone.voidWorld.ZoneMaker;

public class Helper {
    public static boolean buildChunks(Player player, JavaPlugin plugin, int chunks, boolean teleport) {
        if (chunks > 4) {
            player.sendMessage("You can't generate a zone or pull more than 4 chunks at once.");
            return true;
        }

        if (!player.getWorld().getName().contains("c_zone")) {
            var control = CreativeZone.getControl();

            control.setPreviousGameMode(player);
        }

        // The real world for the dimension the player is in, never a zone
        World sourceWorld = getSourceWorld(player.getWorld());

        WorldCreator creativeZone = VoidWorld.getVoidWorld(player.getUniqueId().toString(), sourceWorld);

        World creativeWorld = creativeZone.createWorld();

        player.setGameMode(GameMode.CREATIVE);

        // Set the data
        ZoneMaker.makeWorld(creativeWorld, sourceWorld, chunks, player.getLocation(), plugin);

        if (teleport) {
            Location playerLocation = player.getLocation();

            playerLocation.setWorld(creativeWorld);

            player.teleport(playerLocation);
        }

        return true;
    }

    public static boolean buildChunks(Player player, JavaPlugin plugin, int chunks) {
        return buildChunks(player, plugin, chunks, true);
    }

    /**
     * Finds the real world matching a world's dimension, so a player standing in a
     * zone
     * resolves back to the world the zone was built from
     *
     * @return World
     */
    public static World getSourceWorld(World world) {
        // Already a real world
        if (!world.getName().contains("c_zone")) {
            return world;
        }

        for (World candidate : Bukkit.getWorlds()) {
            if (candidate.getName().contains("c_zone")) {
                continue;
            }

            if (candidate.getEnvironment() == world.getEnvironment()) {
                return candidate;
            }
        }

        // Nothing matched, fall back to the main world
        return Bukkit.getWorlds().get(0);
    }

    public static void teleportToZone(Player player, Player zoneOwner, String world) {
        teleportToZone(player, zoneOwner.getUniqueId().toString(), world);
    }

    public static void teleportToZone(Player player, String zoneOwner, String world) {
        VoidWorld.getVoidWorld(zoneOwner, player.getWorld()).createWorld();

        var zone = new WorldCreator("c_zones/" + zoneOwner + world).createWorld();

        var location = player.getLocation();

        location.setWorld(zone);

        player.teleport(location);
    }

    public static void teleportToZone(Player player, String zoneOwnerUUID, String world, Location location) {
        VoidWorld.getVoidWorld(zoneOwnerUUID, player.getWorld()).createWorld();

        var zone = new WorldCreator("c_zones/" + zoneOwnerUUID + world).createWorld();

        location.setWorld(zone);

        player.teleport(location);
    }

    public static String getOwnerUUID(String worldID) {
        // Pull the uuid out of the zone name
        String uuid = worldID.substring("c_zones/".length());

        int suffixStart = uuid.indexOf("_");

        if (suffixStart != -1) {
            uuid = uuid.substring(0, suffixStart);
        }

        return uuid;
    }

    /**
     * Check if a world is a zone
     * 
     * @param world
     * @return True if it is a zone
     */
    public static boolean isZone(String world) {
        return world.contains("c_zones/");
    }

    /**
     * Check if a world is a zone
     * 
     * @param world
     * @return True if it is a zone
     */
    public static boolean isZone(World world) {
        return isZone(world.getName());
    }

    public static Player getPlayerFromName(String player) {
        return Bukkit.getPlayer(player);
    }

    public static String buildWorldName(Player player, String suffix) {
        return player.getUniqueId().toString() + suffix;
    }
}