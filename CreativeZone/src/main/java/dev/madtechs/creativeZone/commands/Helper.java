package dev.madtechs.creativeZone.commands;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dev.madtechs.creativeZone.voidWorld.VoidWorld;
import dev.madtechs.creativeZone.voidWorld.ZoneMaker;

public class Helper {
    public static void buildChunks(Player player, JavaPlugin plugin, int chunks, boolean teleport) {
        WorldCreator creativeZone = VoidWorld.getVoidWorld();

        World currentWorld = new WorldCreator("world").createWorld();

        // Generate the creative world
        World creativeWorld = creativeZone.createWorld();

        player.setGameMode(GameMode.CREATIVE);

        // Set the data
        ZoneMaker.makeWorld(creativeWorld, currentWorld, chunks, player.getLocation(), plugin);
    
        if (teleport) {
            Location playerLocation = player.getLocation();

            playerLocation.setWorld(creativeWorld);

            player.teleport(playerLocation);
        }
    }

    public static void buildChunks(Player player, JavaPlugin plugin, int chunks) {
        buildChunks(player, plugin, chunks, true);
    }
}
