package dev.madtechs.creativeZone.voidWorld;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ZoneMaker {
    public static void makeWorld(@NotNull World zone, @NotNull World templateWorld, int chunks,
            @NotNull Location playerLocation, @NotNull Plugin plugin) {
        int maxHeight = templateWorld.getMaxHeight();
        int minHeight = templateWorld.getMinHeight();

        int playerChunkX = playerLocation.getChunk().getX();
        int playerChunkZ = playerLocation.getChunk().getZ();

        for (int cx = (playerChunkX - chunks); cx <= (playerChunkX + chunks); cx++) {
            for (int cz = (playerChunkZ - chunks); cz <= (playerChunkZ + chunks); cz++) {
                int chunkX = cx;
                int chunkZ = cz;

                var logger = plugin.getLogger();

                // logger.log(Level.INFO, "Chunk loading at: [" + chunkX + "," + chunkZ + "]");

                templateWorld.getChunkAtAsync(chunkX, chunkZ, true)
                        .thenAccept(chunk -> {

                            Bukkit.getScheduler().runTask(plugin, () -> {

                                Chunk targetChunk = zone.getChunkAt(chunkX, chunkZ);

                                // Place all the blocks in the chunk
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
