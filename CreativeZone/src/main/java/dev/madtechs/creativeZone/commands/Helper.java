package dev.madtechs.creativeZone.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dev.madtechs.creativeZone.CreativeZone;
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
     * Finds the real world matching a world's dimension, so a player standing in a zone
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
}