package dev.madtechs.creativeZone.voidWorld;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class ZoneMaker {
    public static void makeWorld(World zone, World currentWorld, int chunks, Location playerLocation, Plugin plugin) {
        int maxHeight = currentWorld.getMaxHeight();
        int minHeight = currentWorld.getMinHeight();

        int playerChunkX = playerLocation.getChunk().getX();
        int playerChunkZ = playerLocation.getChunk().getZ();

        for (int cx = playerChunkX; cx <= playerChunkX + chunks; cx++) {
            for (int cz = playerChunkZ; cz <= playerChunkZ + chunks; cz++) {
                int chunkX = cx - chunks / 2;
                int chunkZ = cz - chunks / 2;

                var logger = plugin.getLogger();

                logger.log(Level.INFO, "Chunk loading at: [" + chunkX + "," + chunkZ + "]");

                assert currentWorld != null;
                currentWorld.getChunkAtAsync(chunkX, chunkZ, true)
                        .thenAccept(chunk -> {

                            Bukkit.getScheduler().runTask(plugin, () -> {

                                assert zone != null;
                                Chunk targetChunk = zone.getChunkAt(chunkX, chunkZ);

                                for (int x = 0; x < 16; x++) {
                                    for (int z = 0; z < 16; z++) {
                                        for (int y = minHeight; y < maxHeight; y++) {
                                            Block source = chunk.getBlock(x, y, z);
                                            targetChunk.getBlock(x, y, z)
                                                    .setBlockData(source.getBlockData(), false);
                                        }
                                    }
                                }
                            });
                        });
            }
        }
    }
}
